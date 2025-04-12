package com.htkj.ruiji.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavOptions
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.htkj.mvvm.bese.BaseFragment
import com.htkj.ruiji.R
import com.htkj.ruiji.databinding.FragmentLoginBinding
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.nav

class LoginFragment :BaseFragment<BaseViewModel,FragmentLoginBinding>() {
    override fun createObserver() {
        Log.e("LoginFragment","createObserver")
    }

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.click=ProxyClick()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("MainActivity3","onDestroy")
    }

    inner class ProxyClick{
        fun toHome(){
            // 导航到HomeFragment并清空导航栈
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.loginFragment, true)
                .build()
            nav().navigate(R.id.action_loginFragment_to_homeFragment2, null, navOptions)
            // 手动移除LoginFragment，确保其被销毁
            parentFragmentManager.beginTransaction()
                .remove(this@LoginFragment)
                .commitAllowingStateLoss()


//            nav().navigate(R.id.action_loginFragment_to_homeFragment2)
//            requireActivity().supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }


    }
}