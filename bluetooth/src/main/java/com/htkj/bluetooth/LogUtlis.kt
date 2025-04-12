package com.htkj.bluetooth

import android.os.Build
import android.util.Log
import androidx.fragment.app.Fragment



fun Any.Loge(content:String){
//    Log.e(this::class.java.name+"",content)
    Log.e(this::class.java.simpleName+"",content)
}

fun Loges(tag:Any,content:String){
    Log.e(tag::class.java.simpleName+"",content)
}

fun LogeTag(tag:String,content:String){
    Log.e(tag,content)
}


