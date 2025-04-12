package com.htkj.ruiji.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.htkj.bluetooth.ConnectState
import com.htkj.bluetooth.ProSearchResult
import com.htkj.ruiji.R
import me.jessyan.autosize.AutoSizeCompat


class BleListAdapter(list:MutableList<ProSearchResult>) :BaseQuickAdapter<ProSearchResult,BaseViewHolder>(R.layout.item_ble_list ,list) {
    override fun convert(holder: BaseViewHolder, item: ProSearchResult) {
        AutoSizeCompat.autoConvertDensityOfGlobal(holder.itemView.resources);
        holder.setText(R.id.tv_name,"${item.searchResult.name}")
        holder.setText(R.id.tv_connect,when(item.mConnectState){
            ConnectState.SUCCESS_CONNECT->{
                "连接成功"
            }
            ConnectState.NO_NOTICE->{
                "通知失败"
            }
            ConnectState.RUN_CONNECT->{
                "连接中"
            }
            ConnectState.FAIL_CONNECT->{
                "连接失败"
            }
            else->{
                "未连接"
            }
        })
    }
}