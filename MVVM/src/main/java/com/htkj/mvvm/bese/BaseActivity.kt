package com.htkj.mvvm.bese

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar
import com.htkj.mvvm.ext.dismissLoadingExt
import com.htkj.mvvm.ext.showLoadingExt
import com.htkj.mvvm.utlis.FinishActivityManager
import me.hgj.jetpackmvvm.base.activity.BaseVmVbActivity
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel


abstract  class BaseActivity<VM : BaseViewModel, VD : ViewDataBinding>  :
    BaseVmVbActivity<VM, VD>() {
    /**
     * 创建观察者
     */
    override fun createObserver(){

    }

    abstract  override fun initView(savedInstanceState: Bundle?);

    /**
     * 打开等待框
     */
    override fun showLoading(message: String) {
        showLoadingExt(message)
    }
    /**
     * 关闭等待框
     */
    override fun dismissLoading() {
        dismissLoadingExt()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // 所有子类都将继承这些相同的属性,请在设置界面之后设置
//        ImmersionBar.with(this)
//            .fullScreen(true)
//            .init()
//        requestedOrientation=ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
//        AutoSize.autoConvertDensity(this,BaseApplication.design_height_in_dp.toFloat(),false)
        FinishActivityManager.getManager().addActivity(this)
        ImmersionBar.with(this)
            .statusBarDarkFont(true)// 设置状态栏字体颜色为深色，同时设置导航栏背景颜色为深色
            .transparentStatusBar()
            .init()
        // 设置系统UI为全屏
//        window.decorView.systemUiVisibility = (
//                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                )
//        SystemUtlis.hideNavKey(this)


        // 设置状态栏和导航栏沉浸式显示
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            window.decorView.systemUiVisibility = (
//                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
////                            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
////                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
////                            or View.SYSTEM_UI_FLAG_FULLSCREEN
////                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                    )
//        }


        super.onCreate(savedInstanceState)
    }

    fun showToast(msg:String){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        FinishActivityManager.getManager().finishActivity(this)
        super.onDestroy()
//        ImmersionBar.destroy(this);
    }

//    override fun finish() {
//        super.finish()
//
//    }




    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

//        if (hasFocus && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            window.decorView.systemUiVisibility = (
//                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
////                            or View.SYSTEM_UI_FLAG_FULLSCREEN
//                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                    )
//        }
    }

    override fun initDataBind(): View? {

//        AutoSize.autoConvertDensity(this,750f,true)
        return super.initDataBind()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
//        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
//            //横屏
//            AutoSize.autoConvertDensity(this,BaseApplication.design_width_in_dp.toFloat(),false)
//        }else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
//            //竖屏
//            AutoSize.autoConvertDensity(this,BaseApplication.design_height_in_dp.toFloat(),false)
//        }
    }

}