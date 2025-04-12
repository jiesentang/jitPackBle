package com.htkj.ruiji.view

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.htkj.ruiji.R
import com.htkj.ruiji.databinding.ViewItemBinding

class ItemView :FrameLayout {
    constructor(context: Context) : super(context) {
        //initView()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        // Constructor implementation
        //  initView()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        // Constructor implementation
        // initView()
    }
    private val mViewItemBinding =ViewItemBinding.inflate(LayoutInflater.from(context));

    private val mAdapters by lazy {
        ItemAdapter(mutableListOf())
    }

    init {
        addView(mViewItemBinding.root)
        mViewItemBinding.rvList.layoutManager=GridLayoutManager(context,4)
        mViewItemBinding.rvList.adapter= this.mAdapters
        setItem("",0, mutableListOf("","",""))
    }




    fun setItem(title:String,number:Int,mutableList: MutableList<String>){
        this.mAdapters.setList(mutableList)



//        mViewItemBinding.llLayout.post {
//            var width=mViewItemBinding.llLayout.width
//
//            val itemWidth=width/4
//
//            mutableList.forEach {
//                // 创建新的 LinearLayout
//                val newLinearLayout = LinearLayout(context).apply {
//                    layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
//                    orientation = LinearLayout.VERTICAL
//                    gravity = Gravity.CENTER
//                }
//                // 创建新的 TextView1
//                val newTextView1 = TextView(context).apply {
//                    layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
//                    text = "7" // 设置新的数字
//                    setTextColor(Color.BLUE) // 设置新的颜色
//                    textSize = 20f // 设置文本大小
//                    setTypeface(null, Typeface.BOLD) // 设置为粗体
//                }
//
//                // 创建新的 TextView2
//                val newTextView2 = TextView(context).apply {
//                    layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
//                    text = "新状态" // 设置新的状态
//                    setTextColor(Color.parseColor("#FF414449")) // 设置文本颜色
//                    textSize = 16f // 设置文本大小
//                }
//
//                // 将 TextView 添加到 LinearLayout
//                newLinearLayout.addView(newTextView1)
//                newLinearLayout.addView(newTextView2)
//                mViewItemBinding.llLayout.addView(newLinearLayout,itemWidth, LinearLayout.LayoutParams.WRAP_CONTENT)
//            }
//        }



    }

    class ItemAdapter(list: MutableList<String>)  :BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_menu ,list){
        override fun convert(holder: BaseViewHolder, item: String) {

        }

    }
}