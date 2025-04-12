package com.htkj.ruiji.fragment

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.htkj.mvvm.bese.BaseFragment
import com.htkj.ruiji.R
import com.htkj.ruiji.adapter.SensorAdapter
import com.htkj.ruiji.databinding.FragmentSensorBinding
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.nav

/**
 * 传感器
 */
class SensorFragment : BaseFragment<BaseViewModel, FragmentSensorBinding>() {

    private val mSensorAdapter by lazy {
        SensorAdapter(mutableListOf("","","","","",""))
    }

    override fun createObserver() {

    }

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.rvList.layoutManager=LinearLayoutManager(requireContext())
        mDatabind.rvList.adapter=mSensorAdapter
        mSensorAdapter.setOnItemClickListener { adapter, view, position ->
            nav().navigate(R.id.action_sensorFragment_to_deviceInfoFragment)
        }
    }
}