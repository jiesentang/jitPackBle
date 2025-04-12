package com.htkj.ruiji.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.htkj.ruiji.R

class NumericalValueView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr)  {

    private var position=0;
    private val mNumericalValueAdapter by lazy {
        NumericalValueAdapter(mutableListOf("加速度","速度","位移","温度","温升","峭度","波峰因数","包络","转速","角度","电压"))
    }

    init {
        View.inflate(context, R.layout.view_numerical_value,this)

        val mRecyclerView=findViewById<RecyclerView>(R.id.rv_recyclerView)
        mRecyclerView.layoutManager=GridLayoutManager(context,4)
        mRecyclerView.adapter=mNumericalValueAdapter


        mNumericalValueAdapter.setOnItemClickListener { adapter, view, position ->
            this.position=position
            mNumericalValueAdapter.notifyDataSetChanged()
        }


    }



    inner class NumericalValueAdapter(list:MutableList<String>?) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_numerical_value,list){
        override fun convert(holder: BaseViewHolder, item: String) {
            holder.setText(R.id.tv_message,item)
            if(position==holder.layoutPosition){
                holder.setTextColor(R.id.tv_message, Color.parseColor("#FFFFFF"))
                holder.setBackgroundColor(R.id.tv_message,Color.parseColor("#4193FF"))
            }else{
                holder.setTextColor(R.id.tv_message, Color.parseColor("#414449"))
                holder.setBackgroundColor(R.id.tv_message,Color.parseColor("#EFEFEF"))
            }
        }
    }

}