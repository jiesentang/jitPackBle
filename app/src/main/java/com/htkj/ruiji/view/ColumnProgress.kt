package com.htkj.ruiji.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.htkj.ruiji.R

class ColumnProgress @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // 视图宽度和高度
    private var mWidth: Int = 0
    private var mHeight: Int = 0

    // 当前进度值
    private var mProgress: Int = 50

    // 用于绘制形状的矩形区域
    private val mRectF = RectF()

    // 绘图画笔
    private val mPaint = Paint().apply { isAntiAlias = true }

    //背景颜色
    private var mProgressBgId:Int
    //滑块颜色
    private var mProgressSolidId:Int

    // 进度条最大值
    private var max: Int = 100

    init {

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColumnProgress)
        try {

            mProgressBgId = typedArray.getResourceId(R.styleable.ColumnProgress_column_bg_color, R.color.colorPrimary)
            mProgressSolidId = typedArray.getResourceId(R.styleable.ColumnProgress_column_solid_color, R.color.theme)

        } finally {
            typedArray.recycle()
        }

        // 重绘视图以应用样式属性
        postInvalidate()
    }

    /**
     * 重写 onMeasure 方法来测量视图的宽度和高度。
     *
     * @param widthMeasureSpec 用于指示宽度的测量规格的参数。
     * @param heightMeasureSpec 用于指示高度的测量规格的参数。
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidth = measuredWidth - 1 // 宽度值
        mHeight = measuredHeight - 1 // 高度值
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mRectF.set(0f, 0f, mWidth.toFloat(), mHeight.toFloat())
        val borderColor = ContextCompat.getColor(context, mProgressBgId)
        mPaint.setColor(borderColor)
        canvas.drawRect(mRectF, mPaint)

        if (mProgress == 0) // 如果进度为0，则不绘制进度条
            return

        // 绘制进度层
        mRectF.set(0f, (mHeight - mProgress.toFloat() / 100f * mHeight), mWidth.toFloat() , mHeight.toFloat() )
        val slidColor = ContextCompat.getColor(context, mProgressSolidId)
        mPaint.setColor(slidColor)
        canvas.drawRect(mRectF, mPaint)
    }
    /**
     * 设置进度条的当前值。
     *
     * @param currentCount 当前进度值。
     */
    fun setProgress(currentCount: Int) {
        this.mProgress = if (currentCount > max) max else currentCount
        postInvalidate() // 请求重新绘制视图
    }

}