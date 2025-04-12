package com.htkj.ruiji.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.components.YAxis.AxisDependency
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.htkj.bluetooth.Loges
import com.htkj.ruiji.databinding.ViewLinechartBinding
import kotlin.random.Random


class LineChartView@JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private val maxDraw=100
    private lateinit var mLineChartView:ViewLinechartBinding

    init {
        mLineChartView=ViewLinechartBinding.inflate(LayoutInflater.from(context))
        addView(mLineChartView.root)
        initChart()

    }



    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return Line data
     */
    private fun initChart() {
        mLineChartView.chart.apply  {
            // 基础配置
            description.isEnabled  = false
            setDrawGridBackground(false)
            setTouchEnabled(true)
            setDragEnabled(true)
            setScaleEnabled(true)
            setPinchZoom(true)
            legend.isEnabled  = true       // 隐藏图例（可选）
            // X轴配置
            xAxis.apply  {
                position = XAxisPosition.BOTTOM
                setDrawGridLines(false)
                setDrawAxisLine(true)
//                setDrawLabels(false)
                granularity = 0f // 确保滑动流畅
              //  axisMinimum = 1f // 从0开始
            }

            // Y轴配置
            axisLeft.apply  {
                setLabelCount(5, false)
            }
            axisRight.isEnabled  = false

            // 视口配置
            setVisibleXRangeMaximum(10f) // 默认显示10个点
            setVisibleXRangeMinimum(5f)  // 最小显示5个点（可选）
//            moveViewToX(0f)
//            animateXY(100,0,Easing.EaseInOutQuad)
//            addNewEntry(0f)
//            mLineChartView.chart.clearValues()  // 立即清除（仅用于初始化坐标轴）
        }

    }

     fun addNewEntry(yValue: Float, index: Int = 0,name:String) {
        val chart = mLineChartView.chart
        val lineData = chart.data  ?: LineData().also { chart.data  = it }
         Loges(this,"entryCount=${lineData.entryCount}   yValue$yValue"  )
        // 获取或创建数据集
        var set = lineData.getDataSetByIndex(index)  ?: createSet(name).also {
            lineData.addDataSet(it)
        }

        val xValue = set.entryCount.toFloat()

        // 裁剪旧数据（保留最后100个）
        // 关键点：用 takeLast 保留最后100个点
         // 关键修复1：裁剪数据并重置X值
         if (set.entryCount  > maxDraw && set is LineDataSet) {
             val keptEntries = set.values.takeLast(maxDraw).mapIndexed  { idx, entry ->
                 Entry(idx.toFloat(),  entry.y) // 重新生成Entry，X值从0开始
             }.toMutableList()
             set.values  = keptEntries
         }
         lineData.addEntry(Entry(xValue,  yValue),index)
        // 动态调整X轴范围
        chart.xAxis.axisMaximum  = xValue
        // 刷新
        chart.notifyDataSetChanged()
        // 自动滚动并保持视口显示最新10个点
        chart.moveViewToX(set.entryCount.toFloat())
        chart.setVisibleXRangeMaximum(10f)



        chart.postInvalidate()
    }




    private fun createSet(name:String): LineDataSet {
        val set = LineDataSet(null, name)
        val colorSet=getRandomColor()
        set.setDrawValues(true)
        set.setCircleColor(colorSet)
        set.color=colorSet
//        set.setDrawValues(false)
        return set
    }


    fun getRandomColor(): Int {
        val red = Random.nextInt(256)
        val green = Random.nextInt(256)
        val blue = Random.nextInt(256)
        return Color.rgb(red,  green, blue)
    }

    fun keepLastEntries(set: LineDataSet, count: Int) {
        val entries = set.values
        if (entries.size  > count) {
            set.removeFirst()
        }
    }

}