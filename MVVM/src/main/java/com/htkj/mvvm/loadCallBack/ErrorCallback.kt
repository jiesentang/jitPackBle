package me.hgj.jetpackmvvm.demo.app.weight.loadCallBack


import com.htkj.mvvm.R
import com.kingja.loadsir.callback.Callback



class ErrorCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.layout_error
    }

}