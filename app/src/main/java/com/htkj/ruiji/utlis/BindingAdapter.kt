package com.htkj.ruiji.utlis

import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.htkj.bluetooth.ConnectState
import com.htkj.bluetooth.ProSearchResult
import com.htkj.ruiji.R

@BindingAdapter("bindConnectionImage")
fun setConnectionImage(view: ImageView, state: ConnectState?) {
    view.setImageResource(
        when(state) {
            ConnectState.SUCCESS_CONNECT -> R.mipmap.ble_connet
            else -> R.mipmap.ble_no_connet
        }
    )
}

@BindingAdapter("deviceName")
fun setDeviceName(view: TextView, mProSearchResult: ProSearchResult?) {
    view.text=if(mProSearchResult!=null&&mProSearchResult.mConnectState==ConnectState.SUCCESS_CONNECT) mProSearchResult.searchResult.name else "连接可用蓝牙"
}

@BindingAdapter("connectionType")
fun setConnectionType(view: TextView, mProSearchResult: ProSearchResult?) {
    if(mProSearchResult!=null&&mProSearchResult.mConnectState==ConnectState.SUCCESS_CONNECT){
        view.text="蓝牙已连接"
        view.setTextColor(Color.parseColor("#4193FF"))
    }else{
        view.text="蓝牙未连接"
        view.setTextColor(Color.parseColor("#476CAA"))
    }
}