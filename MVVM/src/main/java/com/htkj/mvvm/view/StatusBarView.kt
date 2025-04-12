package com.htkj.mvvm.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.htkj.mvvm.utlis.SystemUtlis

class StatusBarView(context: Context,
                    attrs: AttributeSet? = null,
                    defStyleAttr: Int = 0):View(context, attrs, defStyleAttr) {
    constructor(context: Context):this(context, null, 0)
    constructor(context: Context,attrs: AttributeSet? = null):this(context, attrs, 0)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(SystemUtlis.getScreenWidth(context),SystemUtlis.getStatusBarHeight(context))
    }

}