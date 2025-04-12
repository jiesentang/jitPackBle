package com.htkj.ruiji.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavOptions
import com.htkj.mvvm.bese.BaseFragment
import com.htkj.ruiji.R
import com.htkj.ruiji.databinding.FragmentRemoteBinding
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.nav

/**
 * 远程
 */
class RemoteFragment() : BaseFragment<BaseViewModel, FragmentRemoteBinding>() {



    override fun createObserver() {

    }

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.click=ProxyClick()
    }

    inner class ProxyClick{
        fun onSensor(){
//            nav().navigate(R.id.sensorFragment)
//            val navOptions = NavOptions.Builder()
//                .setPopUpTo(R.id.homeFragment, true)
//                .build()
//            nav().navigate(R.id.toLoginFragment, null, navOptions)
//            // 获取包含ViewPager2的MainFragment
//            val mainFragment = parentFragment
////            homeFragment.finsh()
//            if(mainFragment is HomeFragment){
//                mainFragment.finsh()
//            }
//            (parentFragment as? HomeFragment)?.finsh()
//            nav().navigate(R.id.toLoginFragment)
//            requireActivity().supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("MainActivity","RemoteFragment  onDestroy")
    }
}