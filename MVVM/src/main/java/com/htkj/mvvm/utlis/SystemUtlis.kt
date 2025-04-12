package com.htkj.mvvm.utlis

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager.NameNotFoundException
import android.content.res.Resources
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.KeyCharacterMap
import android.view.KeyEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.WindowManager


object SystemUtlis {
    /**
     * 是否显示虚拟按键
     */
    fun isNavigationBarShow(activity: Activity): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            val display = activity.windowManager.defaultDisplay
            val size = Point()
            val realSize = Point()
            display.getSize(size)
            display.getRealSize(realSize)
            val result = realSize.y != size.y
            realSize.y != size.y
        } else {
            val menu = ViewConfiguration.get(activity).hasPermanentMenuKey()
            val back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)
            if (menu || back) {
                false
            } else {
                true
            }
        }
    }

    /**
     * 隐藏虚拟按键
     */
     fun hideNavKey(context: Context) {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            val v = (context as Activity).window.decorView
            v.systemUiVisibility = View.GONE
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            val decorView = (context as Activity).window.decorView
            val uiOptions = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    )
            decorView.systemUiVisibility = uiOptions
        }
    }

    fun dp2px(dpValue: Float): Int {
        return (0.5f + dpValue * Resources.getSystem().displayMetrics.density).toInt()
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    fun px2dp(pxValue: Float): Float {
        return pxValue / Resources.getSystem().displayMetrics.density
    }

    /**
     * 获取屏幕宽度
     */
    fun getScreenWidth(context: Context): Int {
        val displayMetrics = DisplayMetrics()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }

    /**
     * 获取状态栏高度
     */
    fun getStatusBarHeight(context: Context): Int {
        val resources = context.resources
        val resourceId =
            resources.getIdentifier("status_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(resourceId)
    }

    /**
     * 获取本地apk的名称
     * @param context 上下文
     * @return String
     */
    fun getAppName(context: Context): String {
        try {
            val e = context.packageManager
            val packageInfo = e.getPackageInfo(context.packageName, 0)
            val labelRes = packageInfo.applicationInfo.labelRes
            return context.resources.getString(labelRes)
        } catch (var4: NameNotFoundException) {
            var4.printStackTrace()
            return "unKnown"
        }
    }

    /**
     * 获取本地Apk版本名称
     * @param context 上下文
     * @return String
     */
    fun getVersionName(context: Context): String {
        var verName = ""
        try {
            verName = context.packageManager.getPackageInfo(context.packageName, 0).versionName
        } catch (e: NameNotFoundException) {
            Log.e(
                "",
                e.message + "获取本地Apk版本名称失败！"
            )
            e.printStackTrace()
        }
        return verName
    }

    /**
     * 获取本地Apk版本号
     * @param context 上下文
     * @return int
     */
    fun getVersionCode(context: Context): Int {
        var verCode = -1
        try {
            verCode = context.packageManager.getPackageInfo(context.packageName, 0).versionCode
        } catch (e: NameNotFoundException) {
            Log.e(
                "AppApplicationMgr",
                e.message + "获取本地Apk版本号失败！"
            )
            e.printStackTrace()
        }
        return verCode
    }

    /**
     * 根据key获取xml中Meta的值
     * @param context 上下文
     * @param key
     * @return
     */
    fun getMetaData(context: Context, key: String?): String {
        var value = ""

        try {
            val e = context.packageManager.getApplicationInfo(context.packageName, 128)
            if (null != e) {
                val metaData = e.metaData as Bundle
                if (null != metaData) {
                    value = metaData.getString(key).toString()
                    if (null == value || value.length == 0) {
                        value = ""
                    }
                }
            }
        } catch (var5: NameNotFoundException) {
            var5.printStackTrace()
        }

        return value
    }

}