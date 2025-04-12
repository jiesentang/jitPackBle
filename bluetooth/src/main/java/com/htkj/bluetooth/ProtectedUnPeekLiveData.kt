package com.htkj.bluetooth

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

open class ProtectedUnPeekLiveData<T> : LiveData<T>() {
    protected var isAllowNullValue: Boolean = false

    private val observers = HashMap<Int, Boolean>()

    fun observeInActivity(activity: AppCompatActivity, observer: Observer<T>) {
        val owner: LifecycleOwner = activity
        val storeId = System.identityHashCode(activity.viewModelStore)
        observe(storeId, owner, observer)
    }

    fun observeInFragment(fragment: Fragment, observer: Observer<T>) {
        val owner = fragment.viewLifecycleOwner
        val storeId = System.identityHashCode(fragment.viewModelStore)
        observe(storeId, owner, observer)
    }

    private fun observe(
        storeId: Int,
        owner: LifecycleOwner,
        observer: Observer<T>
    ) {
        if (observers[storeId] == null) {
            observers[storeId] = true
        }

        super.observe(owner, Observer { t: T ->
            if (!observers[storeId]!!) {
                observers[storeId] = true
                if (t != null || isAllowNullValue) {
                    observer.onChanged(t)
                }
            }
        })
    }

    /**
     * 重写的 setValue 方法，默认不接收 null
     * 可通过 Builder 配置允许接收
     * 可通过 Builder 配置消息延时清理的时间
     *
     *
     * override setValue, do not receive null by default
     * You can configure to allow receiving through Builder
     * And also, You can configure the delay time of message clearing through Builder
     *
     * @param value
     */
    public override fun setValue(value: T?) {
        if (value != null || isAllowNullValue) {
            for (entry in observers.entries) {
                entry.setValue(false)
            }
            super.setValue(value)
        }
    }

    protected fun clear() {
        super.setValue(null)
    }
}
