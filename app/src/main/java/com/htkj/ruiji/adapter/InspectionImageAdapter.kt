package com.htkj.ruiji.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.htkj.ruiji.R

class InspectionImageAdapter (list: MutableList<String>) : BaseQuickAdapter<String, BaseViewHolder>(
    R.layout.item_inspection_image ,list) {
    override fun convert(holder: BaseViewHolder, item: String) {

    }
}