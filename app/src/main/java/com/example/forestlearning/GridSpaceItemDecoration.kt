package com.example.forestlearning

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

// spancount는 한 행에 위치할 수 있는 아이템 최대 갯수
// space는 아이템 간의 간격
class GridSpaceItemDecoration(private val spancount: Int, private val space: Int): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % spancount + 1

        if (position < spancount) {
            outRect.top = 30
        }

        outRect.left = space
        outRect.bottom = 30
    }
}