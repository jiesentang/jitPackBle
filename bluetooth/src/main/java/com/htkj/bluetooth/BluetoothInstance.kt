package com.htkj.bluetooth

import android.content.Context
import android.os.Looper
import android.text.TextUtils
import com.inuker.bluetooth.library.BluetoothClient
import com.inuker.bluetooth.library.Constants
import com.inuker.bluetooth.library.connect.listener.BleConnectStatusListener
import com.inuker.bluetooth.library.connect.listener.BluetoothStateListener
import com.inuker.bluetooth.library.connect.options.BleConnectOptions
import com.inuker.bluetooth.library.connect.response.BleNotifyResponse
import com.inuker.bluetooth.library.model.BleGattProfile
import com.inuker.bluetooth.library.receiver.listener.BluetoothBondListener
import com.inuker.bluetooth.library.search.SearchRequest
import com.inuker.bluetooth.library.search.SearchResult
import com.inuker.bluetooth.library.search.response.SearchResponse
import java.util.Locale
import java.util.UUID


object BluetoothInstance {
    val mMap= mutableMapOf<String, ProSearchResult>()
    lateinit var mClient:BluetoothClient;
    private var mProSearchResult:ProSearchResult?=null
    //接收消息
    val messageLiveData = ProtectedUnPeekLiveData<ByteArray>()
    //蓝牙状态
    val mBluetoothState=ProtectedUnPeekLiveData<ProSearchResult>()
    //蓝牙开关
    val mBluetoothSwitch=ProtectedUnPeekLiveData<Boolean>()
    //蓝牙扫描列表
    val mBluetoothSearch=ProtectedUnPeekLiveData<SearchData>()

    private val mBluetoothStateListener: BluetoothStateListener = object : BluetoothStateListener() {
        override fun onBluetoothStateChanged(openOrClosed: Boolean) {
            mBluetoothSwitch.value=openOrClosed
            if(!openOrClosed){
                mProSearchResult=null;
                mMap.clear()
                mBluetoothSearch.value=SearchData(SearchSyatue.END_SEARCH, mutableListOf())
            }
        }
    }

    private val mBleConnectStatusListener: BleConnectStatusListener =object :BleConnectStatusListener(){
        override fun onConnectStatusChanged(mac: String?, status: Int) {
            Loges(this@BluetoothInstance,"${mac}=//=${status}")
            mBluetoothState.value?.let {
                if(it.mac==mac){
                    //Constants.BOND_BONDED
                    if(status!=Constants.REQUEST_SUCCESS){
                        it.mConnectState=ConnectState.FAIL_CONNECT
                        mBluetoothState.value=it
                    }
                    mClient.registerConnectStatusListener(mac,this)
                    Loges(this@BluetoothInstance,"${mac}==${status}")
                }
            }
        }


    }

    fun initBluetooth(context: Context){
        mClient = BluetoothClient(context)
        mClient.registerBluetoothStateListener(mBluetoothStateListener);


        mBluetoothSwitch.value=mClient.isBluetoothOpened
    }

    fun setOpenBluetooth(isOpen:Boolean){
        if(isOpen!=mClient.isBluetoothOpened){
            if(isOpen){
                if(!mClient.openBluetooth()){
                    mBluetoothSwitch.value=false
                }
            }else{
//                mClient.closeBluetooth()
                if(!mClient.closeBluetooth()){
                    mBluetoothSwitch.value=true
                }
            }
        }
    }


    /**
     * 停止扫描
     */
    fun searchStop(){
        mClient.stopSearch()
    }
//    var mSearchBluetooth:((SearchSyatue, MutableList<ProSearchResult>)->Unit)?=null
//    fun addOnSearchBluetooth(search:((SearchSyatue, MutableList<ProSearchResult>)->Unit)?){
//        this.mSearchBluetooth=search;
//    }
//    fun cancelSearchBluetooth(){
//        this.mSearchBluetooth=null;
//    }
    fun searchStart(){
        if(!BluetoothInstance::mClient.isInitialized){
            mBluetoothSearch.value=SearchData(SearchSyatue.FLIE_SEARCH, mutableListOf())
            //没有初始化
            throw NullPointerException("你还没有初始化，请在application中调用initBluetooth")
        }
        val request: SearchRequest = SearchRequest.Builder()
            .searchBluetoothLeDevice(3000, 1) // 先扫BLE设备3次，每次3s
            .searchBluetoothClassicDevice(0) // 再扫经典蓝牙5s
            .searchBluetoothLeDevice(0) // 再扫BLE设备2s
            .build()
        searchStop()
        if(mClient.isBluetoothOpened){
            mClient.search(request, object : SearchResponse {
                override fun onSearchStarted() {
                    mBluetoothSearch.value=SearchData(SearchSyatue.START_SEARCH, mutableListOf())
//                    mSearchBluetooth?.invoke(SearchSyatue.START_SEARCH, mutableListOf())
                }
                override fun onDeviceFounded(device: SearchResult?) {
                    if(device!=null && !TextUtils.isEmpty(device.name) && device.name !="NULL"){
                        if(mProSearchResult!=null&&device.address==mProSearchResult!!.mac){
                            mMap[device.address] = ProSearchResult(device,device.address,mProSearchResult!!.mConnectState)
                        }else{
                            mMap[device.address] = ProSearchResult(device,device.address,ConnectState.NO_CONNECT)
                        }
                        mBluetoothSearch.value=SearchData(SearchSyatue.START_SEARCH, mMap.values.toMutableList())
//                        mSearchBluetooth?.invoke(SearchSyatue.START_SEARCH, mMap.values.toMutableList())
                    }
                }
                override fun onSearchStopped() {
                    Loge("onSearchStopped是否主线程${Looper.getMainLooper()== Looper.myLooper()}")
                    mBluetoothSearch.value=SearchData(SearchSyatue.END_SEARCH, mMap.values.toMutableList())
//                    mSearchBluetooth?.invoke(SearchSyatue.END_SEARCH,mMap.values.toMutableList())
                }
                override fun onSearchCanceled() {
                    Loge("onSearchCanceled是否主线程${Looper.getMainLooper()== Looper.myLooper()}")
                    mBluetoothSearch.value=SearchData(SearchSyatue.END_SEARCH, mMap.values.toMutableList())
//                    mSearchBluetooth?.invoke(SearchSyatue.END_SEARCH,mMap.values.toMutableList())
                }
            })
        }else{
            mBluetoothSearch.value=SearchData(SearchSyatue.FLIE_SEARCH, mMap.values.toMutableList())
        }
    }

    fun connect(mProSearchResult:ProSearchResult){
        if(!mClient.isBluetoothOpened){
            return
        }
        mBluetoothState.value= mProSearchResult.apply {
            mConnectState=ConnectState.RUN_CONNECT
        }
        this.mProSearchResult=mProSearchResult;
        this.mMap[mProSearchResult.mac]=mProSearchResult

        val options = BleConnectOptions.Builder()
            .setConnectRetry(3) // 连接如果失败重试3次
            .setConnectTimeout(3000) // 连接超时30s
            .setServiceDiscoverRetry(3) // 发现服务如果失败重试3次
            .setServiceDiscoverTimeout(2000) // 发现服务超时20s
            .build()
        Loge("connect  ${mProSearchResult.mac}")
        mClient.connect(mProSearchResult.mac,options){ code, data ->
            if (code == Constants.REQUEST_SUCCESS) {
                //连接成功

                mProSearchResult.mUUIDBean=getUUIDService(data).apply {
                    mac=mProSearchResult.mac
                }
                if (mProSearchResult.mUUIDBean!!.isRead()) {
                    openNotify(mProSearchResult)
                }else{
                    //没有找到能接收的特征
                    disconnect();
                    mBluetoothState.value= mProSearchResult.apply {
                        this.mConnectState=ConnectState.FAIL_CONNECT
                    }
                    Loge("没有找到能接收的特征---")
                }
            }else{
                mBluetoothState.value= mProSearchResult.apply {
                    this.mConnectState=ConnectState.FAIL_CONNECT
                }
                Loge("连接失败---${code}")
            }
        }
    }
    fun disconnect(){

        this.mProSearchResult?.let {
            mClient.unregisterConnectStatusListener(it.mac,mBleConnectStatusListener)
            mClient.disconnect(it.mac)
            mClient.clearRequest(it.mac, 0)
            mClient.refreshCache(it.mac)
//            mClient.unregisterConnectStatusListener(it.mac,mBleConnectStatusListener)
        }
    }


    enum class SearchSyatue{
        START_SEARCH, //开始搜索
        END_SEARCH,   //结束搜索
        FLIE_SEARCH,      //扫描异常
    }

    data class SearchData(val mSearchSyatue:SearchSyatue,val mProSearchResultList:MutableList<ProSearchResult>)



    private fun getUUIDService(bleGattProfile: BleGattProfile): UUIDBean {
        val uuidBean = UUIDBean()
        for (service in bleGattProfile.services) {
            val characters = service.characters
            Loges(this@BluetoothInstance,"服务特征" + service.uuid)
            characters.forEach {
                Loges(this@BluetoothInstance,"发送接收${it.uuid} " )
            }
            if (isMyService(service.uuid)) {
                uuidBean.serveUuid=service.uuid
                characters.forEach {
                    uuidBean.writeUuid=it.uuid
                    uuidBean.readUuid=it.uuid
                }
            }
        }
        return uuidBean
    }

    /**
     * 判断蓝牙服务是否是参加自定义的
     * @param uuid
     * @return
     */
    private fun isMyService(uuid: UUID): Boolean {
        val uuidString = "0x" + uuid.toString().substring(4, 8).uppercase(Locale.getDefault())
        return (HexUtil.hex16StringToInt10(uuidString) == HexUtil.hex16StringToInt10("0x180f"))
    }


    private var messageVolue: ByteArray? = null
    /**
     * 开启通知
     */
    private  fun openNotify(mProSearchResult:ProSearchResult){
        Loge(mProSearchResult.mUUIDBean.toString())
        mClient.notify(mProSearchResult.mUUIDBean?.mac,mProSearchResult.mUUIDBean?.serveUuid,mProSearchResult.mUUIDBean?.readUuid,object :
            BleNotifyResponse {
            override fun onResponse(code: Int) {
                Loges( this@BluetoothInstance,"openNotify=开启通知状态$code")
                if (code == Constants.REQUEST_SUCCESS) {
                    mClient.registerConnectStatusListener(mProSearchResult.mUUIDBean?.mac,mBleConnectStatusListener)
                    mBluetoothState.value=mProSearchResult.apply {
                        this.mConnectState=ConnectState.SUCCESS_CONNECT
                    }
                } else {
                    //没有找到能接收的特征
                    disconnect()
                    this@BluetoothInstance.mProSearchResult?.mConnectState=ConnectState.NO_NOTICE
                    mBluetoothState.value=mProSearchResult.apply {
                        this.mUUIDBean=null
                        this.mConnectState=ConnectState.NO_NOTICE
                    }
//                    callback.invoke(ConnectState.NO_NOTICE)
                    Loges(this@BluetoothInstance,"通知开启失败---")
                }

            }

            override fun onNotify(service: UUID?, character: UUID?, value: ByteArray?) {
                Loges(this@BluetoothInstance,"onNotify=${HexUtil.encodeHexStr(value)}")
                if(messageVolue==null){
                    messageVolue = value
                }else{
                    messageVolue = ByteUtils.concat(messageVolue, value)
                }
                ProvingUtil.analysis(messageVolue,object: OnAnalysisCallback {
                    override fun onAnalysisSuccess(message: ByteArray?) {
                        Loges(this@BluetoothInstance,"完整消息=${HexUtil.encodeHexStr(message)}")
                        messageLiveData.value = message
                        messageVolue=null;
//                        message?.let {
//                            onBluetoothCallback.onMessage(it)
//                        }
                    }

                    override fun onAnalysisDeletion(deletionMessage: ByteArray?) {
                        Loges(this@BluetoothInstance,"缺失数据=" + HexUtil.encodeHexStr(deletionMessage))
                        messageVolue = deletionMessage
                    }

                    override fun onAnalysisFail(msg: String?) {
                        Loges(this@BluetoothInstance,"解析失败=$msg")
                        messageVolue = null
                    }
                })
            }

        })
    }


    /**
     * 分包发送
     * @param data
     * @param onBleSendResponse
     */
    fun aggregationWrite(data: ByteArray, onBleSendResponse: ((Int,String)->Unit)?) {
        Loge( "发送数据" + HexUtil.formatHexString(data, true))
        if( this@BluetoothInstance.mProSearchResult?.mConnectState==ConnectState.SUCCESS_CONNECT&&
            this@BluetoothInstance.mProSearchResult?.mUUIDBean!=null){
            if (data.size < 21) {
                write(data,this@BluetoothInstance.mProSearchResult?.mUUIDBean!!, onBleSendResponse)
            } else {
                val splitByteArray: List<ByteArray> = ByteUtil.splitByteArray(data, 20)
                recursionWrite(splitByteArray, 0,this@BluetoothInstance.mProSearchResult?.mUUIDBean!!, onBleSendResponse)
            }
        }else{
            //设备没有连接
            onBleSendResponse?.invoke(Constants.BLUETOOTH_DISABLED,"设备没有连接")
        }


    }

    /**
     * 递归发送分包消息
     * @param splitByteArray
     * @param index
     */
    private fun recursionWrite(
        splitByteArray: List<ByteArray>,
        index: Int,
        mUUIDBean:UUIDBean,
        onBleSendResponse: ((Int,String)->Unit)?
    ) {
        if (index < splitByteArray.size) {
            write(splitByteArray[index],mUUIDBean){code,message->
                if(code==Constants.REQUEST_SUCCESS){
                    recursionWrite(splitByteArray,index+1,mUUIDBean,onBleSendResponse)
                }else{
                    onBleSendResponse?.invoke(code,message)
                }
            }
        } else {
            //发送成功
            onBleSendResponse?.invoke(Constants.REQUEST_SUCCESS,"发送成功")
        }
    }


    /**
     * // 操作成功（值为0）
     *     public static final int REQUEST_SUCCESS = 0;
     *     // 通用操作失败（值为-1）
     *     public static final int REQUEST_FAILED = -1;
     *     // 操作被取消（值为-2）
     *     public static final int REQUEST_CANCELED = -2;
     *     // 参数非法（值为-3）
     *     public static final int ILLEGAL_ARGUMENT = -3;
     *     // 设备不支持BLE蓝牙（值为-4）
     *     public static final int BLE_NOT_SUPPORTED = -4;
     *     // 蓝牙未开启（值为-5）
     *     public static final int BLUETOOTH_DISABLED = -5;
     *     // 蓝牙服务未就绪（值为-6）
     *     public static final int SERVICE_UNREADY = -6;
     *     // 操作超时（值为-7）
     *     public static final int REQUEST_TIMEDOUT = -7;
     *     // 请求队列溢出（值为-8）
     *     public static final int REQUEST_OVERFLOW = -8;
     *     // 请求被拒绝（值为-9）
     *     public static final int REQUEST_DENIED = -9;
     *     // 请求过程中发生异常（值为-10）
     *     public static final int REQUEST_EXCEPTION = -10;
     *     // 未知错误（值为-11）
     *     public static final int REQUEST_UNKNOWN = -11;
     *
     */

    /**
     * 已知bytess少于20，直接发送
     * @param bytes
     * @param onBleSendResponse
     */
    private fun write(bytes: ByteArray,  mUUIDBean:UUIDBean, onBleSendResponse: ((Int,String)->Unit)?) {
        Loge("写入数据,mac=${mUUIDBean.mac} serveUuid=${ mUUIDBean.serveUuid} writeUuid=${mUUIDBean.writeUuid}" )
        mClient.write(
            mUUIDBean.mac, mUUIDBean.serveUuid, mUUIDBean.writeUuid, bytes
        ) { code ->
            onBleSendResponse?.invoke(code,"")
//            if (onBleSendResponse != null) {
//                if (code == Constants.REQUEST_SUCCESS) {
//                    //                        Log.e(TAG,"发送成功");
//                    onBleSendResponse.invoke()
//                } else {
//                    onBleSendResponse.onSendFail(1, "发送失败")
//                }
//            }
        }
    }

}