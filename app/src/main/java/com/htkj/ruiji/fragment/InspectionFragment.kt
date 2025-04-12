package com.htkj.ruiji.fragment

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.htkj.mvvm.bese.BaseFragment
import com.htkj.ruiji.R
import com.htkj.ruiji.adapter.InspectionAdaper
import com.htkj.ruiji.adapter.MaintenanceAdapter
import com.htkj.ruiji.databinding.FragmentInspectionBinding
import com.htkj.ruiji.manager.MyLayoutManager
import com.htkj.ruiji.viewmodel.InspectViewModel
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.nav

/**
 * 我的巡检  异常/正常
 */
class InspectionFragment : BaseFragment<InspectViewModel, FragmentInspectionBinding>() {
    private val mInspectionAdaper by lazy {
        InspectionAdaper(mutableListOf("","","",""))
    }
    override fun createObserver() {

    }

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.rvList.layoutManager= MyLayoutManager(requireContext())
        mDatabind.rvList.adapter=mInspectionAdaper

        mInspectionAdaper.setOnItemClickListener { adapter, view, position ->
            nav().navigate(R.id.action_myInspectionFragment_to_inspectionInfoFragment)
        }
    }
}