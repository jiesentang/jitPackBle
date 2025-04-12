package com.htkj.ruiji.manager

import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * 适用于父类和子Item宽度固定，确保子Item可以均匀分布在一行内的布局
 * itemWidth为子Item的宽度
 * rowSpacing为每行之间的间距
 * isFirstLeftAndEndRightZero，是否每行首尾两个子Item紧贴父View的边缘。
 * 是的话，需要时在外面单独设置父View的paddingLeft和right即可。适用于子Item外面背景不要求展示下相同背景的情况
 * 否的话，就是每个子Item的左右边距相同，适用于子Item外面背景展示相同大小。此时一般还需要重写onDraw
 *
 */
class GridAverageSpaceItemDecoration (horizontal: Int, vertical: Int):
    RecyclerView.ItemDecoration() {

    private val spaceHorizontal by lazy { horizontal }
    private val spaceVertical by lazy { vertical }
    private var spanCount = -1

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (parent.layoutManager is GridLayoutManager) {
            if (spanCount == -1) {
                spanCount =
                    (parent.layoutManager as GridLayoutManager).spanCount
            }
            val position = parent.getChildAdapterPosition(view)
            if (position != 0) { /* 排除第一项 */
                if (position < spanCount) { /* 第一行其余项 */
                    outRect.left = spaceHorizontal
                } else if (position % spanCount == 0) { /* 其余行第一项（左起第一列） */
                    outRect.top = spaceVertical
                } else { /* 剩余的项 */
                    outRect.top = spaceVertical
                    outRect.left = spaceHorizontal
                }
            }
        } else {
            Log.e("GridItemDecoration", "layoutManager isn't GridLayoutManager")
        }
    }



}
