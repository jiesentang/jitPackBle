package com.htkj.mvvm.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import com.htkj.mvvm.R

class RoundImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private var width: Float = 0f
    private var height: Float = 0f
    private var leftTopRadius: Int = 0
    private var rightTopRadius: Int = 0
    private var leftBottomRadius: Int = 0
    private var rightBottomRadius: Int = 0

    init {
        if (Build.VERSION.SDK_INT < 18) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        }
        init(context,attrs)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        width = getWidth().toFloat()
        height = getHeight().toFloat()
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        // 读取自定义属性
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.Round_Angle_Image_View)
        val radius = typedArray.getDimensionPixelOffset(R.styleable.Round_Angle_Image_View_radius, defaultRadius)
        leftTopRadius = typedArray.getDimensionPixelOffset(R.styleable.Round_Angle_Image_View_left_top_radius, defaultRadius)
        rightTopRadius = typedArray.getDimensionPixelOffset(R.styleable.Round_Angle_Image_View_right_top_radius, defaultRadius)
        leftBottomRadius = typedArray.getDimensionPixelOffset(R.styleable.Round_Angle_Image_View_left_bottom_radius, defaultRadius)
        rightBottomRadius = typedArray.getDimensionPixelOffset(R.styleable.Round_Angle_Image_View_right_bottom_radius, defaultRadius)

        // 如果四个角的值没有设置，就用通用的 radius
        if (leftTopRadius == defaultRadius) {
            leftTopRadius = radius
        }
        if (rightTopRadius == defaultRadius) {
            rightTopRadius = radius
        }
        if (leftBottomRadius == defaultRadius) {
            leftBottomRadius = radius
        }
        if (rightBottomRadius == defaultRadius) {
            rightBottomRadius = radius
        }
        typedArray.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        if (width > 12 && height > 12) {
            val path = Path()
            /* 向路径中添加圆角矩形。radii 数组定义圆角矩形的四个圆角的 x,y 半径。radii 长度必须为 8 */
            val rids = floatArrayOf(
                leftTopRadius.toFloat(), leftTopRadius.toFloat(),
                rightTopRadius.toFloat(), rightTopRadius.toFloat(),
                leftBottomRadius.toFloat(), leftBottomRadius.toFloat(),
                rightBottomRadius.toFloat(), rightBottomRadius.toFloat()
            )
            path.addRoundRect(RectF(0f, 0f, width, height), rids, Path.Direction.CW)
            canvas.clipPath(path)
        }
        super.onDraw(canvas)
    }

    companion object {
        private const val defaultRadius = 0
    }
}