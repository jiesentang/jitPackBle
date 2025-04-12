package com.htkj.ruiji.fragment

import android.os.Bundle
import com.htkj.mvvm.bese.BaseFragment
import com.htkj.ruiji.R
import com.htkj.ruiji.databinding.FragmentMyBinding
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.nav

/**
 * 个人信息
 */
class MyFragment : BaseFragment<BaseViewModel, FragmentMyBinding>() {
    override fun createObserver() {

    }

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.click=ProxyClick();
    }

    inner class ProxyClick{
        fun toAbout(){
            nav().navigate(R.id.action_homeFragment_to_aboutFragment)
        }
        fun toMyInspection(){
            nav().navigate(R.id.action_homeFragment_to_myInspectionFragment)
        }

    }
}