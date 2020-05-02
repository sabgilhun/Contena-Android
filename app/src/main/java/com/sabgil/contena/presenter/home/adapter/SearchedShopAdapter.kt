package com.sabgil.contena.presenter.home.adapter

import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sabgil.contena.R
import com.sabgil.contena.common.ext.autoNotify
import com.sabgil.contena.common.ext.layoutInflater
import com.sabgil.contena.databinding.ItemSearchedShopBinding
import com.sabgil.contena.presenter.home.model.SearchedShop
import kotlinx.android.synthetic.main.item_subscribed_shop.view.*
import kotlin.properties.Delegates

class SearchedShopAdapter(
    private val onToggleSubscription: (Boolean, String) -> Unit
) : RecyclerView.Adapter<SearchedShopAdapter.SearchedShopViewHolder>() {

    private var searchedShopItems: List<SearchedShop> by Delegates.observable(mutableListOf())
    { _, old, new ->
        autoNotify(old, new) { o, n ->
            o.shopName == n.shopName
        }
    }

    fun replaceAll(searchedShops: List<SearchedShop>) {
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
        ).apply {
            binding.root.subscribeButton.setOnClickListener {
                searchedShopItems[adapterPosition].let {
                    onToggleSubscription(!it.subscribed, it.shopName)
                }
            }
        }

    override fun getItemCount(): Int = searchedShopItems.size

    override fun onBindViewHolder(holder: SearchedShopViewHolder, position: Int) {
        holder.binding.item = searchedShopItems[position]
    }

    class SearchedShopViewHolder(
        val binding: ItemSearchedShopBinding
    ) : RecyclerView.ViewHolder(binding.root)
}
