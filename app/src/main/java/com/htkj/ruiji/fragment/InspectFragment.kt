package com.htkj.ruiji.fragment

import android.Manifest
import android.os.Build
import android.os.Bundle
import com.htkj.bluetooth.BluetoothInstance
import com.htkj.bluetooth.Loges
import com.htkj.mvvm.bese.BaseFragment
import com.htkj.ruiji.R
import com.htkj.ruiji.databinding.FragmentInspectBinding
import com.htkj.ruiji.eventViewModel
import com.htkj.ruiji.utlis.Loge
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.nav
import pub.devrel.easypermissions.EasyPermissions

/**
 * 巡检
 */
class InspectFragment : BaseFragment<BaseViewModel, FragmentInspectBinding>(), EasyPermissions.PermissionCallbacks {


    private val permissions by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            arrayOf(
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.ACCESS_FINE_LOCATION, //
                Manifest.permission.ACCESS_COARSE_LOCATION, //
                Manifest.permission.BLUETOOTH_CONNECT,   //
                Manifest.permission.BLUETOOTH_SCAN,      //
                Manifest.permission.BLUETOOTH_ADVERTISE  //
            )
        } else {
            arrayOf(
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        }
    }

    val RC_BLUETOOTH_PERM=1000;

    override fun createObserver() {
        eventViewModel.mDataMap.observe(this){
            Loges(this@InspectFragment,it.toString())
//            Loges(this@InspectFragment,"00="+it.get(byteArrayOf(0x00,0x02)).toString())
//            Loges(this@InspectFragment,"02="+it[byteArrayOf(0x00,0x02)].toString())
//            Loges(this@InspectFragment,"04="+it[byteArrayOf(0x00,0x04)].toString())
            mDatabind.mLineChartView.addNewEntry(it["0000"]?:0f,0,"X")
            mDatabind.mLineChartView.addNewEntry(it["0002"]?:0f,1,"Y")
            mDatabind.mLineChartView.addNewEntry(it["0004"]?:0f,2,"Z")
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.click=ProxyClick()
        mDatabind.instance=BluetoothInstance
    }

    fun postPermission(){
        if (EasyPermissions.hasPermissions(requireContext(), *permissions)) {
            // 已经拥有权限，启动服务
            nav().navigate(R.id.action_homeFragment_to_bleListFragment)
        } else {
            // 请求权限
            EasyPermissions.requestPermissions(
                this,
                "需要蓝牙和位置权限以扫描和连接蓝牙设备",
                RC_BLUETOOTH_PERM,
                *permissions
            )
        }
    }

    inner class ProxyClick{
        fun onBleList(){
            postPermission()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if (requestCode == RC_BLUETOOTH_PERM) {
            // 权限被授予，启动服务
            nav().navigate(R.id.action_homeFragment_to_bleListFragment)
        }

    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (requestCode == RC_BLUETOOTH_PERM) {
            // 权限被拒绝，显示提示信息
            showToast("缺少必要权限，功能可能无法正常工作")
            Loge("perms=${perms}")
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // 将结果传递给 EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
}