package com.htkj.ruiji.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.htkj.ruiji.R

class SensorAdapter(list:MutableList<String>):BaseQuickAdapter<String,BaseViewHolder>(R.layout.item_sensor,list) {
    override fun convert(holder: BaseViewHolder, item: String) {

    }
}