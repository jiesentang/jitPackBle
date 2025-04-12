package com.htkj.ruiji.fragment

import android.graphics.Rect
import android.os.Bundle
import android.view.ViewTreeObserver
import com.htkj.mvvm.bese.BaseFragment
import com.htkj.mvvm.utlis.SystemUtlis
import com.htkj.ruiji.adapter.InspectionImageAdapter
import com.htkj.ruiji.adapter.InspectionItemsAdapter
import com.htkj.ruiji.databinding.FragmentInspectionInfoBinding
import com.htkj.ruiji.manager.GridAverageSpaceItemDecoration
import com.htkj.ruiji.manager.MyGridManager
import com.htkj.ruiji.manager.MyLayoutManager
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel


/**
 * 我的巡检详情
 */
class InspectionInfoFragment : BaseFragment<BaseViewModel, FragmentInspectionInfoBinding>() {

    private val mInspectionItemsAdapter by lazy {
        InspectionItemsAdapter(mutableListOf("","","","","","","","","","","","","","","","","",""))
    }

    private val mInspectionImageAdapter by lazy {
        InspectionImageAdapter(mutableListOf(""))
    }

    override fun createObserver() {

    }

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.rvList.layoutManager=MyLayoutManager(requireContext())
        mDatabind.rvList.adapter=mInspectionItemsAdapter
        mDatabind.mImageList.layoutManager=MyGridManager(requireContext(),2)
        mDatabind.mImageList.addItemDecoration(GridAverageSpaceItemDecoration(SystemUtlis.dp2px(10f),SystemUtlis.dp2px(10f)))
        mDatabind.mImageList.adapter=mInspectionImageAdapter



//        mDatabind.etInput.setOnFocusChangeListener { v, hasFocus ->
//
//            if (hasFocus) {
//                mDatabind.svScrollview.post {
//                    mDatabind.svScrollview.smoothScrollTo(0, mDatabind.etInput.top-20);
//                }
//            }
//
//        }

//        mDatabind.root.getViewTreeObserver()
//            .addOnGlobalLayoutListener(ViewTreeObserver.OnGlobalLayoutListener {
//                val r = Rect()
//                mDatabind.root.getWindowVisibleDisplayFrame(r)
//                val heightDiff: Int = mDatabind.root.getRootView().getHeight() - (r.bottom - r.top)
//                if (heightDiff > 200) { // 如果高度变化大于200，认为是软键盘弹出
//                    // 软键盘弹出
//                    mDatabind.svScrollview.smoothScrollTo(0, mDatabind.etInput.top-20);
//                }
//            })


    }
}