package com.htkj.ruiji.viewmodel

import com.htkj.bluetooth.ProtectedUnPeekLiveData
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

class InspectViewModel: BaseViewModel() {
   public val sConnect = ProtectedUnPeekLiveData<Boolean>().apply {
        value=false
    }
}