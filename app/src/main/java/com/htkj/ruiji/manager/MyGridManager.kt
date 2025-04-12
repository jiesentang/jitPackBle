package com.htkj.ruiji.manager

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager

class MyGridManager(val context: Context, spanCount:Int):GridLayoutManager(context,spanCount ) {
    override fun canScrollVertically(): Boolean {
        return false
    }
}