package com.sabgil.contena.presenter.manage.adapter

import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sabgil.contena.R
import com.sabgil.contena.common.ext.layoutInflater
import com.sabgil.contena.databinding.ItemSubscribedShopBinding
import com.sabgil.contena.domain.model.Shop

class SubscribedShopAdapter : RecyclerView.Adapter<SubscribedShopAdapter.ShopManageViewHolder>() {

    private val shopItems: MutableList<Shop> = mutableListOf()

    fun replaceAll(shopList: List<Shop>) {
        shopItems.clear()
        shopItems.addAll(shopList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopManageViewHolder =
        ShopManageViewHolder(
            DataBindingUtil.inflate(
                parent.context.layoutInflater,
                R.layout.item_subscribed_shop,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = shopItems.size

    override fun onBindViewHolder(holder: ShopManageViewHolder, position: Int) {
        holder.binding.shop = shopItems[position]
    }

    class ShopManageViewHolder(
        val binding: ItemSubscribedShopBinding
    ) : RecyclerView.ViewHolder(binding.root)
}
