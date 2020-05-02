package com.sabgil.contena.common.ext

import androidx.recyclerview.widget.DiffUtil
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

fun <T> RecyclerView.Adapter<*>.autoNotify(old: List<T>, new: List<T>, compare: (T, T) -> Boolean) {
    val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return compare(old[oldItemPosition], new[newItemPosition])
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return old[oldItemPosition] == new[newItemPosition]
        }

        override fun getOldListSize() = old.size

        override fun getNewListSize() = new.size
    })

    diff.dispatchUpdatesTo(this)
}