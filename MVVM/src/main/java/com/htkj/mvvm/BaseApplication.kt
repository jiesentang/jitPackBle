package com.htkj.mvvm

import com.tencent.mmkv.MMKV
import me.hgj.jetpackmvvm.base.BaseApp

open  class BaseApplication : BaseApp() {

    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this.filesDir.absolutePath + "/mmkv")



    }
}