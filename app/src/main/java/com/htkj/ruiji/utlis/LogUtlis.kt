package com.htkj.ruiji.utlis

import android.os.Build
import android.util.Log
import androidx.fragment.app.Fragment



fun Any.Loge(content:String){
//    Log.e(this::class.java.name+"",content)
    Log.e(this::class.java.simpleName+"",content)
}