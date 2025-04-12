package com.htkj.ruiji.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.htkj.mvvm.bese.BaseFragment

class HomeAdapter(fragmentActivity: FragmentActivity,val list:MutableList<BaseFragment<*,*>>) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
       return list.size;
    }

    override fun createFragment(position: Int): Fragment {
        return list[position]
    }
}