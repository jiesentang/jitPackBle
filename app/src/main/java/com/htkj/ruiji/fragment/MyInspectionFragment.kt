package com.htkj.ruiji.fragment

import android.os.Bundle
import com.htkj.mvvm.bese.BaseFragment
import com.htkj.ruiji.adapter.RepairAdapter
import com.htkj.ruiji.databinding.FragmentMaintenanceBinding
import com.htkj.ruiji.databinding.FragmentMyInspectionBinding
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

/**
 * 我的巡检
 */
class MyInspectionFragment: BaseFragment<BaseViewModel, FragmentMyInspectionBinding>() {
    private val mFragmentList= mutableListOf<BaseFragment<*,*>>();
    override fun createObserver() {

    }

    override fun initView(savedInstanceState: Bundle?) {
        mFragmentList.add(InspectionFragment())
        mFragmentList.add(InspectionFragment())
        val mHomeAdapter= RepairAdapter(childFragmentManager,mFragmentList, arrayOf("正常","异常"))
        mDatabind.vpPager.adapter=mHomeAdapter
        mDatabind.stlLayout.setViewPager(mDatabind.vpPager)
    }
}