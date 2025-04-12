package com.htkj.ruiji.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.htkj.mvvm.bese.BaseFragment

class RepairAdapter(fragmentManager: FragmentManager, val list:MutableList<BaseFragment<*,*>>,val lists:Array<String>) : FragmentPagerAdapter(fragmentManager) {
    override fun getCount(): Int {
        return list.size;
    }

    override fun getItem(position: Int): Fragment {
        return list[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return lists[position]
    }


}