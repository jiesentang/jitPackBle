package com.htkj.ruiji.fragment

import android.os.Bundle
import com.htkj.mvvm.bese.BaseFragment
import com.htkj.ruiji.adapter.HomeAdapter
import com.htkj.ruiji.adapter.RepairAdapter
import com.htkj.ruiji.databinding.FragmentRepairBinding
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

/**
 * 保修
 */
class RepairFragment : BaseFragment<BaseViewModel, FragmentRepairBinding>() {
    private val mFragmentList= mutableListOf<BaseFragment<*,*>>();
    override fun createObserver() {

    }

    override fun initView(savedInstanceState: Bundle?) {
        mFragmentList.add(MaintenanceFragment())
        mFragmentList.add(MaintenanceFragment())
        mFragmentList.add(MaintenanceFragment())
        mFragmentList.add(MaintenanceFragment())
        val mHomeAdapter= RepairAdapter(childFragmentManager,mFragmentList, arrayOf("内容","待处理","处理中","已结单"))
        mDatabind.vpPager.adapter=mHomeAdapter
        mDatabind.stlLayout.setViewPager(mDatabind.vpPager)
    }
}