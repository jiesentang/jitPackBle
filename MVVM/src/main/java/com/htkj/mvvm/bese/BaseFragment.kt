package com.htkj.mvvm.bese

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import com.htkj.mvvm.ext.dismissLoadingExt
import com.htkj.mvvm.ext.showLoadingExt
import me.hgj.jetpackmvvm.base.fragment.BaseVmDbFragment
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel


abstract class BaseFragment<VM: BaseViewModel,DB: ViewDataBinding> :BaseVmDbFragment<VM,DB>() {
     abstract override fun createObserver()


     abstract override fun initView(savedInstanceState: Bundle?)

     override fun lazyLoadData() {

     }
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

     fun showToast(msg: String){
         Toast.makeText(requireContext(),msg,Toast.LENGTH_SHORT).show()
     }
    protected var isBinding=false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        AutoSize.autoConvertDensity(requireActivity(),375f,true)
        val view=super.onCreateView(inflater, container, savedInstanceState)
        isBinding=true;
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        isBinding=false;
    }


//    private var popupView: LoadingPopupView? = null
//    protected fun showLoading(content: String?) {
//        if (popupView == null) {
//            popupView = XPopup.Builder(requireContext()).dismissOnTouchOutside(false)
//                .asLoading(content)
//        }
//        popupView!!.setTitle(content)
//        if (!popupView!!.isShow) {
//            popupView!!.show()
//        }
//    }
//
//    protected fun hideLoading() {
//        popupView?.let {
//            popupView!!.dismiss()
//        }
//    }
//
//    protected fun hideLoading(content: String?) {
//        popupView?.let {
//            it.setTitle(content)
//            (it as BasePopupView).delayDismiss(1000)
//        }
//    }


 }