package com.sabgil.contena.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlin.properties.Delegates

abstract class MultiViewTypeAdapter : RecyclerView.Adapter<BindingViewHolder>() {

    protected abstract val viewTypeMap: ViewTypeMap

    private var items: List<BaseItem> by Delegates.observable(mutableListOf())
    { _, old, new -> autoNotify(old, new) { o, n -> o.id == n.id } }

    fun replaceAll(items: List<BaseItem>) {
        this.items = items.toList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val binding = DataBindingUtil
            .inflate<ViewDataBinding>(
                LayoutInflater.from(parent.context),
                viewType,
                parent,
                false
            )
        val viewHolder = BindingViewHolder(binding)
        viewTypeMap.getOnCreateViewHolder(viewType)(binding) {
            viewHolder.adapterPosition.let { if (it != -1) items[it] else null }
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        val item = items[position]
        viewTypeMap.getOnBindViewHolder(item::class.java)(item, holder.binding, position)
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) =
        viewTypeMap.getLayoutId(items[position]::class.java)
}

private fun <T> RecyclerView.Adapter<*>.autoNotify(
    old: List<T>,
    new: List<T>,
    compare: (T, T) -> Boolean
) {
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