package com.htkj.mvvm.ext

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.htkj.mvvm.utlis.SystemUtlis
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView
import java.lang.ref.WeakReference

//loading框
private var loadingDialog: WeakReference<LoadingPopupView>? = null

/**
 * 打开等待框
 */
fun AppCompatActivity.showLoadingExt(message: String = "请求网络中") {
    if (!this.isFinishing) {
        if (loadingDialog == null) {
            loadingDialog = WeakReference(XPopup.Builder(this).hasNavigationBar(false).asLoading(message)) ;
        }
        if(loadingDialog!!.get()==null){
            loadingDialog = WeakReference(XPopup.Builder(this).hasNavigationBar(false).asLoading(message)) ;
        }
        loadingDialog?.get()?.setTitle(message)
        loadingDialog?.get()?.show()
    }
}

/**
 * 打开等待框
 */
fun Fragment.showLoadingExt(message: String = "请求网络中") {
    activity?.let {
        if (!it.isFinishing) {
            if (loadingDialog == null) {
                loadingDialog = WeakReference(XPopup.Builder(it).hasNavigationBar(false).asLoading(message)) ;
            }
            if(loadingDialog!!.get()==null){
                loadingDialog = WeakReference(XPopup.Builder(it).hasNavigationBar(false).asLoading(message)) ;
            }
            loadingDialog?.get()?.setTitle(message)
            loadingDialog?.get()?.show()
        }
    }
}

/**
 * 关闭等待框
 */
fun Activity.dismissLoadingExt() {
    loadingDialog?.get()?.dismiss()
    loadingDialog?.clear()


}

/**
 * 关闭等待框
 */
fun Fragment.dismissLoadingExt() {
    loadingDialog?.get()?.dismiss()
    loadingDialog?.clear()

}
