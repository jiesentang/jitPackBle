package com.htkj.ruiji.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.htkj.ruiji.R

class MaintenanceAdapter(list: MutableList<String>) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_maintenance ,list) {
    override fun convert(holder: BaseViewHolder, item: String) {
//        holder.setText(R.id.tv_name,item+"")
    }
}