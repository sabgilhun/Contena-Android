package com.sabgil.contena.presenter.home.adapter

import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sabgil.contena.R
import com.sabgil.contena.common.ext.layoutInflater
import com.sabgil.contena.databinding.ItemShopShortcutBinding
import com.sabgil.contena.domain.model.Shop

class ShopShortcutAdapter : RecyclerView.Adapter<ShopShortcutAdapter.ShopShortCutViewHolder>() {

    private val shopItems: MutableList<Shop> = mutableListOf()

    fun addAll(shopList: List<Shop>) {
        shopItems.clear()
        shopItems.addAll(shopList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopShortCutViewHolder =
        ShopShortCutViewHolder(
            DataBindingUtil.inflate(
                parent.context.layoutInflater,
                R.layout.item_shop_shortcut,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = shopItems.size

    override fun onBindViewHolder(holder: ShopShortCutViewHolder, position: Int) {
        holder.binding.shop = shopItems[position]
    }

    class ShopShortCutViewHolder(
        val binding: ItemShopShortcutBinding
    ) : RecyclerView.ViewHolder(binding.root)
}
