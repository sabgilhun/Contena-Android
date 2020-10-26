package com.sabgil.contena.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class MultiTypeAdapter(
    private val viewTypeMap: ViewTypeMap
) : RecyclerView.Adapter<BindingViewHolder>() {

    private var items: List<BaseItem> = emptyList()

    fun replaceAll(items: List<BaseItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            viewType,
            parent,
            false
        )
        viewTypeMap.getOnCreate(viewType).onCreate(binding)
        return BindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        val item = items[position]
        viewTypeMap.getOnBind(items[position]::class.java).onBind(item, holder.binding, position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return viewTypeMap.getLayoutId(items[position]::class.java)
    }
}