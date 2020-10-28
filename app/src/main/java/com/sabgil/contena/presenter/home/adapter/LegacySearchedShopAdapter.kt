package com.sabgil.contena.presenter.home.adapter

import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sabgil.contena.R
import com.sabgil.contena.common.ext.autoNotify
import com.sabgil.contena.common.ext.layoutInflater
import com.sabgil.contena.databinding.ItemSearchedShopBinding
import com.sabgil.contena.domain.model.Shop
import kotlin.properties.Delegates

class LegacySearchedShopAdapter(
    private val onToggleSubscription: (Boolean, String) -> Unit
) : RecyclerView.Adapter<LegacySearchedShopAdapter.SearchedShopViewHolder>() {

    private var searchedShopItems: List<Shop> by Delegates.observable(mutableListOf())
    { _, old, new ->
        autoNotify(old, new) { o, n ->
            o.shopName == n.shopName
        }
    }

    fun replaceAll(searchedShops: List<Shop>) {
        searchedShopItems = searchedShops
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

    override fun getItemCount(): Int = searchedShopItems.size

    override fun onBindViewHolder(holder: SearchedShopViewHolder, position: Int) {
//        holder.binding.item = searchedShopItems[position]
    }

    class SearchedShopViewHolder(
        val binding: ItemSearchedShopBinding
    ) : RecyclerView.ViewHolder(binding.root)
}
