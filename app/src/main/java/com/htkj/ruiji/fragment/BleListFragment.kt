package com.htkj.ruiji.fragment

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.htkj.bluetooth.BluetoothInstance
import com.htkj.bluetooth.ConnectState
import com.htkj.bluetooth.HexUtil
import com.htkj.bluetooth.LogeTag
import com.htkj.bluetooth.Loges
import com.htkj.mvvm.bese.BaseFragment
import com.htkj.ruiji.R
import com.htkj.ruiji.adapter.BleListAdapter
import com.htkj.ruiji.databinding.FragmentBleListBinding
import com.htkj.ruiji.viewmodel.BleListViewModel
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

/**
 * 蓝牙扫描列表
 */
class BleListFragment : BaseFragment<BleListViewModel, FragmentBleListBinding>() {

    // 在 Activity 或 Fragment 中获取

    private val mBleListAdapter by lazy {
        BleListAdapter(mutableListOf())
    }


    override fun createObserver() {
        BluetoothInstance.messageLiveData.observeInFragment(this){
            LogeTag("activityViewModels_BleList",HexUtil.encodeHexStr(it))
        }
        //蓝牙状态
        mViewModel.mBluetoothSearchState.observeInFragment(this){
            if(it.mSearchSyatue == BluetoothInstance.SearchSyatue.START_SEARCH||it.mSearchSyatue == BluetoothInstance.SearchSyatue.END_SEARCH){
                mBleListAdapter.setList(it.mProSearchResultList)
            } else {
                showToast("蓝牙扫描失败")
                mBleListAdapter.setList(mutableListOf())
            }
            if(it.mSearchSyatue == BluetoothInstance.SearchSyatue.END_SEARCH||it.mSearchSyatue ==BluetoothInstance.SearchSyatue.FLIE_SEARCH){
                mDatabind.mSmartRefreshLayout.finishRefresh()
            }
        }
        mViewModel.mBleSwitch.observeInFragment(this){


            if(it){
                mDatabind.mSmartRefreshLayout.autoRefresh()
            }
        }
        mViewModel.mState.observeInFragment(this){ observe->
            LogeTag("BleListFragment",observe.toString())
            mBleListAdapter.notifyItemChanged(mBleListAdapter.data.indexOfFirst { it.mac==observe.mac })
        }




//        LogeTag("activityViewModels","哈希=${messageViewModel.hashCode()}")
    }

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.viewModel=mViewModel
        mDatabind.rvList.layoutManager= LinearLayoutManager(requireContext())
        mDatabind.rvList.adapter=mBleListAdapter
        mDatabind.mSmartRefreshLayout.setEnableLoadMore(false)
        mDatabind.mSmartRefreshLayout.setOnRefreshListener {
            if(mViewModel.mBleSwitch.value == true){
                BluetoothInstance.searchStart()
            }else{
                showToast("请开启蓝牙")
                mDatabind.mSmartRefreshLayout.finishRefresh(100)
            }
        }
        if(mViewModel.mBleSwitch.value == true){
            mDatabind.mSmartRefreshLayout.autoRefresh()
        }

        mBleListAdapter.addChildClickViewIds(R.id.tv_connect)
        mBleListAdapter.setOnItemChildClickListener { adapter, view, position ->
            if(view.id==R.id.tv_connect){
                BluetoothInstance.connect(mBleListAdapter.data[position]);
            }
        }
        mDatabind.bleSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            BluetoothInstance.setOpenBluetooth(isChecked)
        }

    }



}