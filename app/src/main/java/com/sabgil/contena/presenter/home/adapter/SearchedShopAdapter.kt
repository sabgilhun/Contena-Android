package com.sabgil.contena.presenter.home.adapter

import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sabgil.contena.R
import com.sabgil.contena.common.ext.layoutInflater
import com.sabgil.contena.databinding.ItemSearchedShopBinding
import com.sabgil.contena.domain.model.Shop

class SearchedShopAdapter : RecyclerView.Adapter<SearchedShopAdapter.SearchedShopViewHolder>() {

    private val shopItems: MutableList<Shop> = mutableListOf()

    fun replaceAll(shopList: List<Shop>) {
        shopItems.clear()
        shopItems.addAll(shopList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchedShopViewHolder =
        SearchedShopViewHolder(
            DataBindingUtil.inflate(
                parent.context.layoutInflater,
                R.layout.item_searched_shop,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = shopItems.size

    override fun onBindViewHolder(holder: SearchedShopViewHolder, position: Int) {
        holder.binding.shop = shopItems[position]
        holder.binding.subscribeButton.setOnClickListener {
            it.isSelected = !(it.isSelected)
        }
    }

    class SearchedShopViewHolder(
        val binding: ItemSearchedShopBinding
    ) : RecyclerView.ViewHolder(binding.root)
}
