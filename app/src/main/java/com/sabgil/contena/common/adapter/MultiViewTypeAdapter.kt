package com.sabgil.contena.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class MultiViewTypeAdapter(
    private val viewTypeMap: ViewTypeMap
) : RecyclerView.Adapter<BindingViewHolder>() {

    private var items: List<BaseItem> = emptyList()

    fun replaceAll(items: List<BaseItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val binding = DataBindingUtil
            .inflate<ViewDataBinding>(
                LayoutInflater.from(parent.context),
                viewType,
                parent,
                false
            )
        viewTypeMap.getOnCreateViewHolder(viewType)(binding)
        return BindingViewHolder(binding)
    }


    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        val item = items[position]
        viewTypeMap.getOnBindViewHolder(item::class.java)(item, holder.binding, position)
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) =
        viewTypeMap.getLayoutId(items[position]::class.java)
}