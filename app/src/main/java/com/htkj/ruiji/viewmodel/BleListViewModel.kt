package com.htkj.ruiji.viewmodel

import com.htkj.bluetooth.BluetoothInstance
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

class BleListViewModel : BaseViewModel() {

    val mState= BluetoothInstance.mBluetoothState
    val mBleSwitch=BluetoothInstance.mBluetoothSwitch
    val mBluetoothSearchState=BluetoothInstance.mBluetoothSearch
    fun getBleList(mSearchResponse:OnSearchResponse?){
        val mSet= mutableSetOf<String>()

    }

      interface OnSearchResponse{
         fun onSearchStarted();
          fun onDeviceFounded(list:MutableList<String>);
          fun onSearchCanceled();
    }

}