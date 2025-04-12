package com.htkj.ruiji

import com.htkj.mvvm.BaseApplication
import com.htkj.ruiji.event.AppViewModel
import com.htkj.ruiji.event.EventViewModel
import com.inuker.bluetooth.library.BluetoothContext
import com.tencent.bugly.crashreport.CrashReport
import me.jessyan.autosize.AutoSizeConfig

//Application全局的ViewModel，里面存放了一些账户信息，基本配置信息等
val appViewModel: AppViewModel by lazy { App.appViewModelInstance }
//Application全局的ViewModel，用于发送全局通知操作
val eventViewModel: EventViewModel by lazy { App.eventViewModelInstance }
class App : BaseApplication() {
    companion object {
        lateinit var instance: App
        lateinit var eventViewModelInstance: EventViewModel
        lateinit var appViewModelInstance: AppViewModel
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
        eventViewModelInstance = getAppViewModelProvider().get(EventViewModel::class.java)
        appViewModelInstance = getAppViewModelProvider().get(AppViewModel::class.java)

        BluetoothContext.set(this);
        CrashReport.initCrashReport(applicationContext, "8615b7f62e", false);
        AutoSizeConfig.getInstance().setCustomFragment(true);

    }
}