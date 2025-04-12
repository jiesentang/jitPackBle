package com.htkj.ruiji.fragment

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.htkj.mvvm.bese.BaseFragment
import com.htkj.ruiji.adapter.MaintenanceAdapter
import com.htkj.ruiji.databinding.FragmentMaintenanceBinding
import com.htkj.ruiji.databinding.FragmentRepairBinding
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

/**
 *报修列表界面
 */
class MaintenanceFragment : BaseFragment<BaseViewModel, FragmentMaintenanceBinding>() {

    private val mMaintenanceAdapter by lazy {
        MaintenanceAdapter(mutableListOf("","","",""))
    }

    override fun createObserver() {

    }

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.rvList.layoutManager=LinearLayoutManager(requireContext())
        mDatabind.rvList.adapter=mMaintenanceAdapter
    }
}