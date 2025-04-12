package com.htkj.ruiji.view
import android.content.Context
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.htkj.ruiji.R


class VerticalProgress @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // 进度条圆角半径
    private var mRadius: Int = 0
    // 进度条是否显示边框
    private var mBorderEnable: Boolean = false
    // 是否启用渐变色
    private var mGradientEnable: Boolean = true
    // 渐变色开始的颜色资源ID
    private var mStartResId: Int = 0
    // 渐变色结束的颜色资源ID
    private var mEndResId: Int = 0
    // 边框的颜色资源ID
    private var mBorderColorResId: Int = 0
    // 进度条背景填充颜色资源ID
    private var mProgressBgColorId: Int = 0
    // 边框宽度（单位：像素）
    private var mBorderWidth: Int = 0

    // 当前进度值
    private var mProgress: Int = 10
    // 进度条最大值
    private var max: Int = 100

    // 视图宽度和高度
    private var mWidth: Int = 0
    private var mHeight: Int = 0

    // 用于绘制形状的矩形区域
    private val mRectF = RectF()
    // 绘图画笔
    private val mPaint = Paint().apply { isAntiAlias = true }

    /**
     * 初始化视图，并获取样式属性。
     *
     * @param context The Context the view is running in.
     * @param attrs   The attributes of the XML tag that is inflating the view.
     */
    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.VerticalProgress)
        try {
            mRadius = typedArray.getInt(R.styleable.VerticalProgress_progress_radius, 0)
            mBorderEnable = typedArray.getBoolean(R.styleable.VerticalProgress_progress_border_enable, false)
            mGradientEnable = typedArray.getBoolean(R.styleable.VerticalProgress_progress_gradient_enable, true)
            mStartResId = typedArray.getResourceId(R.styleable.VerticalProgress_progress_start_color, R.color.colorPrimary)
            mProgressBgColorId = typedArray.getResourceId(R.styleable.VerticalProgress_progress_solid_color, R.color.white)
            mEndResId = typedArray.getResourceId(R.styleable.VerticalProgress_progress_end_color, R.color.theme)
            mBorderColorResId = typedArray.getResourceId(R.styleable.VerticalProgress_progress_border_color, R.color.teal_700)
            mBorderWidth = typedArray.getDimensionPixelSize(R.styleable.VerticalProgress_progress_border_width, 10)
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

    /**
     * 重写 onDraw 方法来绘制视图的内容。
     *
     * @param canvas 画布对象，可以在上面绘制各种图形和文字。
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (mRadius == 0) {
            // 如果未设置圆角半径，则将其设置为宽度的一半
            mRadius = mWidth / 2
        }

        if (mBorderEnable) {
            // 绘制边框层
            mRectF.set(0f, 0f, mWidth.toFloat(), mHeight.toFloat())
            val borderColor = ContextCompat.getColor(context, mBorderColorResId)
            mPaint.setColor(borderColor)
            canvas.drawRoundRect(mRectF, mRadius.toFloat(), mRadius.toFloat(), mPaint)

            // 绘制背景层
            mRectF.set(mBorderWidth.toFloat(), mBorderWidth.toFloat(), mWidth.toFloat() - mBorderWidth, mHeight.toFloat() - mBorderWidth)
            val progressBgColor = ContextCompat.getColor(context, mProgressBgColorId)
            mPaint.setColor(progressBgColor)
            canvas.drawRoundRect(mRectF, mRadius.toFloat(), mRadius.toFloat(), mPaint)
        }

        if (mProgress == 0) // 如果进度为0，则不绘制进度条
            return

        val section = mProgress.toFloat() / max.toFloat()

        // 绘制进度层
        mRectF.set(8f, (mHeight - mProgress.toFloat() / 100f * mHeight + 10f), mWidth.toFloat() - 8f, mHeight.toFloat() - 8f)
        if (mGradientEnable) {
            // 创建线性渐变色
            val shader = LinearGradient(
                0f, 0f, mWidth.toFloat() * section, mHeight.toFloat(),
                ContextCompat.getColor(context, mStartResId), ContextCompat.getColor(context, mEndResId), Shader.TileMode.CLAMP
            )
            mPaint.shader = shader
        }
        canvas.drawRoundRect(mRectF, mRadius.toFloat(), mRadius.toFloat(), mPaint)

        // 清除渐变器
        mPaint.shader = null
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