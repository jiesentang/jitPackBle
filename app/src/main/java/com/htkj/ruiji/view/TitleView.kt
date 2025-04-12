package com.htkj.ruiji.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.htkj.ruiji.R
import com.htkj.ruiji.databinding.ViewTitleBinding
import me.hgj.jetpackmvvm.ext.nav

class TitleView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private  var titleName:String?
    private  var imageRightResId:Int
    private  var textRight:String?

    init {
        val mViewTitleBinding  =ViewTitleBinding.inflate(LayoutInflater.from(context))
        addView(mViewTitleBinding.root)
        mViewTitleBinding.click=ProxyClick()
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleView)
        try {
            // 获取 title_name 属性的字符串值
             titleName = typedArray.getString(R.styleable.TitleView_title_name)
            // 获取 image_right 属性的资源ID（对于 dimension 类型的属性）
             imageRightResId = typedArray.getResourceId(R.styleable.TitleView_image_right, 0)
            // 获取 text_right 属性的字符串值
             textRight = typedArray.getString(R.styleable.TitleView_text_right)
        } finally {
            typedArray.recycle()
        }
        mViewTitleBinding.tvTitle.text=titleName?:""

    }




    inner class ProxyClick{
        fun onBack(){
            nav(this@TitleView).navigateUp()
        }
    }

}