package com.example.mymovies.presentation.item

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class HorizontalItemDecoration (
    private val spacing: Int,
    private val includeEdge: Boolean = false
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val itemCount = parent.adapter?.itemCount ?: 0

        if (includeEdge) {
            when (position) {
                0 -> {
                    // Первый элемент
                    outRect.left = spacing
                    outRect.right = spacing
                }
                itemCount - 1 -> {
                    // Последний элемент
                    outRect.left = spacing
                    outRect.right = spacing
                }
                else -> {
                    // Средние элементы
                    outRect.left = spacing
                    outRect.right = spacing
                }
            }
        } else {
            // Простые отступы между элементами
            outRect.right = spacing
            if (position == 0) {
                outRect.left = 0
            } else if(position ==  itemCount - 1){
                outRect.right = 0
            }
        }

        outRect.top = 0
        outRect.bottom = 0
    }
}