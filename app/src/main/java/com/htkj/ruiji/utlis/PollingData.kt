package com.htkj.ruiji.utlis

import android.annotation.SuppressLint
import android.os.Build
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.htkj.bluetooth.BluetoothInstance
import com.htkj.bluetooth.HexUtil
import com.htkj.bluetooth.Loges
import com.htkj.ruiji.eventViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import java.nio.ByteBuffer
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference
import kotlin.experimental.inv

/**
 * 蓝牙指令轮询控制器
 * 实现生命周期感知的蓝牙消息轮询发送/接收机制
 * @param lifecycleOwner 生命周期所有者（通常是Activity/Fragment）
 */
class PollingData(private val lifecycleOwner: LifecycleOwner) : DefaultLifecycleObserver {
    // 指令映射表（键为指令特征码字符串，值为存储响应数据的列表）
    private val messageQueueMap = LinkedHashMap<String, Float>().apply {
        put("0000", 0f)
        put("0002", 0f)
        put("0004", 0f)
    }

    // 原子操作变量（线程安全）
    private val currentCommand = AtomicReference<String>() // 当前执行的指令（改为String类型）
    private val isActive = AtomicBoolean(false)            // 轮询激活标志
    private val retryCount = AtomicInteger(0)              // 当前指令重试次数计数器

    // 协程控制
    private var pollingJob: Job? = null                    // 轮询协程任务
    private val responseChannel = Channel<Boolean>(        // 响应通道
        Channel.RENDEZVOUS                                 // 无缓冲通道
    )

    // 超时配置
    private val timeoutDuration = 1000L                    // 单次指令等待响应超时时间（毫秒）
    private val maxRetries = 10                            // 单条指令最大重试次数

    /**
     * 生命周期回调 - 组件创建时初始化
     */
    @SuppressLint("DefaultLocale")
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        // 监听蓝牙响应数据
        BluetoothInstance.messageLiveData.observe(owner)  { receivedData ->
            // 仅在轮询激活状态且数据有效时处理
            if (isActive.get()  && receivedData.size  >= 3) {
                Loges(this@PollingData, HexUtil.encodeHexStr(receivedData))

                val currentCmd = currentCommand.get()
                // 验证是否为当前指令的预期响应（比较特征码）
                if (currentCmd != null && currentCmd == HexUtil.encodeHexStr(receivedData.copyOfRange(1,  3))) {
                    val intValue = byteArrayToSignedFloat(receivedData.copyOfRange(5,  7))
                    // 存储响应数据
                    messageQueueMap[currentCmd] = String.format("%.2f",  intValue).toFloat()
                    val currentIndex = messageQueueMap.keys.indexOf(currentCmd)
                    if (currentIndex == messageQueueMap.size-1) {
                        // 一轮完成
                        Loges(this@PollingData, "一轮=$messageQueueMap")
                        eventViewModel.mDataMap.value  = messageQueueMap
                    }
                    // 切换到下一条指令
                    moveToNextCommand()

                    // 发送成功信号
                    responseChannel.trySend(true)
                }
            }
        }
    }

    /**
     * 启动轮询
     */
    fun startLoop() {
        // 防止重复启动
        if (isActive.getAndSet(true))  return
        Loges(this,"启动轮询")
        pollingJob = lifecycleOwner.lifecycleScope.launch  {
            // 初始化第一条指令
            if (messageQueueMap.isNotEmpty())  {
                currentCommand.set(messageQueueMap.keys.first())
            }

            // 轮询主循环
            while (isActive)  {
                try {
                    // 发送当前指令
                    sendCurrentCommand()
                    Loges(this@PollingData, "等待响应或超时")
                    // 等待响应或超时
                    val receivedResponse = withTimeoutOrNull(timeoutDuration) {
                        responseChannel.receive()
                    } ?: false
                    Loges(this@PollingData, "等待结束")
                    if (!receivedResponse) {
                        Loges(this@PollingData, "超时，重新发送当前指令")
                        // 处理超时情况
                        handleTimeout()
                    } else {
                        // 成功收到响应则重置重试计数器
                        retryCount.set(0)
                    }

                    // 计算动态间隔（确保所有指令在1秒内完成一轮）
//                    delay(50)
                } catch (e: Exception) {
                    when (e) {
                        is CancellationException -> return@launch // 协程正常取消
                        else -> e.printStackTrace()                // 打印其他异常
                    }
                }
            }
        }
    }

    /**
     * 发送当前指令
     */
    private suspend fun sendCurrentCommand() {
        val currentCmd = currentCommand.get()  ?: return
        Loges(this@PollingData, "发送$currentCmd")
        // 将字符串指令转换为字节数组
        val cmdBytes = currentCmd.hexStringToByteArray()
        // 发送蓝牙指令（异步回调）
        BluetoothInstance.aggregationWrite(assemblyData(cmdBytes))  { code, _ ->
            // 发送失败时通知通道
            if (code != 0) {
                lifecycleOwner.lifecycleScope.launch  {
                    Loges(this@PollingData, "发送失败$code")
                    responseChannel.trySend(false)
                }
            }
            // 发送成功由蓝牙响应数据观察者处理
        }
    }

    /**
     * 处理超时情况
     */
    private suspend fun handleTimeout() {
        val currentRetry = retryCount.incrementAndGet()
        // 达到最大重试次数则跳过当前指令
        if (currentRetry >= maxRetries) {
            moveToNextCommand()
            retryCount.set(0)
        }
    }

    /**
     * 移动到下一条指令
     */
    private fun moveToNextCommand() {
        val currentCmd = currentCommand.get()  ?: return
        val commands = messageQueueMap.keys.toList()
        val currentIndex = commands.indexOf(currentCmd)

        if (currentIndex != -1) {
            // 环形队列方式获取下一条指令
            val nextIndex = (currentIndex + 1) % commands.size
            currentCommand.set(commands[nextIndex])
        }
    }

    /**
     * 停止轮询
     */
    fun stopLoop() {
        isActive.set(false)        // 设置停止标志
        responseChannel.trySend(true)
        pollingJob?.cancel()     // 取消协程
        Loges(this,"停止轮询")
    }

    /**
     * 生命周期回调 - 组件销毁时清理资源
     */
    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        stopLoop()  // 确保资源释放
        responseChannel.close()    // 关闭通道
    }

    /**
     * 组装指令数据（添加帧头、帧尾和CRC校验）
     * @param bytes 原始指令数据
     * @return 完整的数据帧
     */
    private fun assemblyData(bytes: ByteArray): ByteArray {
        // 帧头(2字节) + 指令数据 + 固定帧尾(2字节)
        val messageByte = byteArrayOf(0xA5.toByte(),  0x84.toByte())  + bytes + byteArrayOf(0x00, 0x01)
        // 添加CRC校验码
        return messageByte + crc8Checkout(messageByte, messageByte.size)
    }

    /**
     * CRC8校验算法
     * @param buff 待校验数据
     * @param length 数据长度
     * @return 校验码
     */
    private fun crc8Checkout(buff: ByteArray, length: Int): Byte {
        var crc = 0
        var currentIndex = 0
        var remainingLength = length

        while (remainingLength-- > 0) {
            crc = crc xor (buff[currentIndex++].toInt() and 0xFF)
            for (i in 0 until 8) {
                crc = if (crc and 1 != 0) {
                    (crc ushr 1) xor 0x8C  // 多项式异或
                } else {
                    crc ushr 1             // 普通右移
                }
            }
        }
        return (crc and 0xFF).toByte()
    }

    fun byteArrayToSignedFloat(bytes: ByteArray): Float {
        require(bytes.size  == 2) { "输入必须为2字节的ByteArray" }

        // 判断符号位（第一个字节的最高位）
        val isNegative = (bytes[1].toInt() and 0x80) != 0

        return if (isNegative) {
            // 负数处理：取反+1得到原码
            val inverted = byteArrayOf(bytes[0].inv(), bytes[1].inv())
            val originalCode = (inverted[0].toInt() and 0xFF) +
                    ((inverted[1].toInt() and 0xFF) shl 8) + 1
            -originalCode / 100f
        } else {
            // 正数处理：直接转换
            ((bytes[1].toInt() and 0xFF) shl 8 or (bytes[0].toInt() and 0xFF)) / 100f
        }
    }

    fun String.hexStringToByteArray():  ByteArray {
        require(length % 2 == 0) { "Hex string must have even length" }
        return chunked(2)
            .map { it.toInt(16).toByte()  }
            .toByteArray()
    }
}