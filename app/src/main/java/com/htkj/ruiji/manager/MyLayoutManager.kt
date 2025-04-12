package com.htkj.ruiji.manager

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager

class MyLayoutManager(val context: Context):LinearLayoutManager(context) {
    override fun canScrollVertically(): Boolean {
        return false
    }
}