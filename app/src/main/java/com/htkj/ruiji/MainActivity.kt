package com.htkj.ruiji
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.htkj.bluetooth.BluetoothInstance
import com.htkj.bluetooth.ConnectState
import com.htkj.mvvm.bese.BaseActivity
import com.htkj.ruiji.databinding.MainLayoutBinding
import com.htkj.ruiji.utlis.PollingData
import com.tencent.bugly.crashreport.CrashReport
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel


class MainActivity : BaseActivity<BaseViewModel,MainLayoutBinding>() {
    private lateinit var navController:NavController



    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun createObserver() {
        super.createObserver()

    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)



        BluetoothInstance.initBluetooth(this)
        navController= Navigation.findNavController(this@MainActivity,R.id.nav_host_fragment)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            // 获取当前目的地的ID
//            val destinationId = destination.id
            // 根据目的地ID执行相应的逻辑
//            when (destinationId) {
//                R.id.homeFragment->{
//
////                    // 获取NavInflater实例来加载导航图
////                    val navInflater = navController.navInflater
////                    val navGraph = navInflater.inflate(R.navigation.main_navigation)
////                    // 设置新的起始目的地
////                    navGraph.startDestination = R.id.homeFragment
////                    //        // 将新的导航图设置到NavController
////                    navController.graph = navGraph
//                }
//            }

//            settingMain()
        }
//        CrashReport.testJavaCrash();

    }

     fun settingMain(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val fragmentManager = navHostFragment.childFragmentManager

        // 获取所有添加到FragmentManager中的Fragment
        val fragments = fragmentManager.fragments
        fragments.forEach {
            Log.e("MainActivity1","${it::class.simpleName}")
        }

        // 过滤导航栈中的Fragment
        val navStackFragments = fragments.filter { it.isVisible }
        navStackFragments.forEach {
            Log.e("MainActivity2","${it::class.simpleName}")
        }

//        return navStackFragments.size
    }
}

