package com.htkj.ruiji.fragment


import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.htkj.bluetooth.BluetoothInstance
import com.htkj.bluetooth.ConnectState
import com.htkj.bluetooth.HexUtil
import com.htkj.bluetooth.LogeTag
import com.htkj.bluetooth.Loges
import com.htkj.mvvm.bese.BaseFragment
import com.htkj.ruiji.R
import com.htkj.ruiji.adapter.HomeAdapter
import com.htkj.ruiji.databinding.FragmentHomeBinding
import com.htkj.ruiji.utlis.Loge
import com.htkj.ruiji.utlis.PollingData
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

/**
 * 首页
 */
class HomeFragment : BaseFragment<BaseViewModel, FragmentHomeBinding>(),EasyPermissions.PermissionCallbacks {//,
    companion object {
        private const val RC_BLUETOOTH_PERM = 123
    }
    private var mFragmentList= mutableListOf<BaseFragment<*,*>>();
    val mPollingData by lazy {
        PollingData(this)
    }

    override fun createObserver() {
        BluetoothInstance.messageLiveData.observeInFragment(this){
            LogeTag("activityViewModels_Home", HexUtil.encodeHexStr(it))
        }
        BluetoothInstance.mBluetoothSwitch.observeInFragment(this){
            if(!it){
                //关闭轮询
                mPollingData.stopLoop()
            }
        }
        BluetoothInstance.mBluetoothState.observeInFragment(this){
            if(it.mConnectState== ConnectState.SUCCESS_CONNECT){
                //开启轮询
                mPollingData.startLoop()
            }else{
                //关闭轮询
                mPollingData.stopLoop()
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        mFragmentList.clear()

        Log.e("MainActivity","HomeFragment onDestroy");
    }

    override fun initView(savedInstanceState: Bundle?) {
//        ( requireActivity() as MainActivity).settingMain()
        Log.e("MainActivity","initView");
        lifecycle.addObserver(mPollingData)
        mFragmentList.add(RemoteFragment())
        mFragmentList.add(InspectFragment())
        mFragmentList.add(RepairFragment())
        mFragmentList.add(MyFragment())
        val mHomeAdapter= HomeAdapter(requireActivity(),mFragmentList)
        mDatabind.vpPager.adapter=mHomeAdapter
        mDatabind.vpPager.offscreenPageLimit=3
        mDatabind.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_remote -> {
                    mDatabind.vpPager.setCurrentItem(0, false)
                }               // 处理第一个导航项的点击事件
                R.id.navigation_inspect -> {
                    mDatabind.vpPager.setCurrentItem(1, false)
                }              // 处理第二个导航项的点击事件
                R.id.navigation_repair -> {
                    mDatabind.vpPager.setCurrentItem(2, false)
                }              // 处理第二个导航项的点击事件
                R.id.navigation_my -> {
                    mDatabind.vpPager.setCurrentItem(3, false)
                }              // 处理第二个导航项的点击事件
            }
            true
        }
        // 当你想要停止滑动时
        mDatabind.vpPager.isUserInputEnabled=false
        mDatabind.vpPager.registerOnPageChangeCallback(object :OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                mDatabind.bottomNavigationView.menu.getItem(position).setChecked(true)
            }
        })
//        requestPermissions()

    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (requestCode == RC_BLUETOOTH_PERM) {
            // 权限被拒绝，显示提示信息
            showToast("缺少必要权限，功能可能无法正常工作")
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        if (requestCode == RC_BLUETOOTH_PERM) {
            // 权限被授予，启动服务
//            startBluetoothService()
        }
    }
//
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // 将结果传递给 EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    @AfterPermissionGranted(RC_BLUETOOTH_PERM)
    private fun requestPermissions() {
        val perms = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            arrayOf(
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.BLUETOOTH_ADVERTISE
            )
        } else {
            arrayOf(
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        }

        if (EasyPermissions.hasPermissions(requireContext(), *perms)) {
            // 已经拥有权限，启动服务
//            startBluetoothService()
        } else {
            // 请求权限
            EasyPermissions.requestPermissions(
                this,
                "需要蓝牙和位置权限以扫描和连接蓝牙设备",
                RC_BLUETOOTH_PERM,
                *perms
            )
        }
    }


//    @SuppressLint("CommitTransaction")
//    fun finsh(){
//        // 手动移除LoginFragment，确保其被销毁
//
//        Loge("finsh-----------")
//        // 获取parentFragmentManager
//        val fragmentManager: FragmentManager = parentFragmentManager
//        // 开启一个事务
//        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
//        // 获取所有Fragment
//        val fragments: List<Fragment> = fragmentManager.getFragments()
//        // 遍历并移除所有Fragment
//        if (fragments != null) {
//            for (fragment in fragments) {
//                if (fragment != null) {
//                    transaction.remove(fragment)
//                }
//            }
//        }
//        // 提交事务
//        transaction.commit()
//    }


}