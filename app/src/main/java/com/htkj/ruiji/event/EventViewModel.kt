package com.htkj.ruiji.event

import androidx.lifecycle.MutableLiveData
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.callback.livedata.event.EventLiveData


class EventViewModel:BaseViewModel() {

    //排序规则
    var mDataMap= MutableLiveData<LinkedHashMap<String,Float>>()

}