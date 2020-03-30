package com.sabgil.contena.common.ext

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.addOnFirstVisibleChangedListener(onChanged: (Int) -> Unit) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        var oldPosition: Int = -1

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val position = (recyclerView.layoutManager as LinearLayoutManager)
                .findFirstVisibleItemPosition()

            if (position != oldPosition) {
                onChanged(position)
                oldPosition = position
            }
        }
    })
}