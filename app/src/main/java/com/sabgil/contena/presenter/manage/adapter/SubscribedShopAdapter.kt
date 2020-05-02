package com.sabgil.contena.presenter.manage.adapter

import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sabgil.contena.R
import com.sabgil.contena.common.ext.autoNotify
import com.sabgil.contena.common.ext.layoutInflater
import com.sabgil.contena.databinding.ItemSubscribedShopBinding
import com.sabgil.contena.presenter.manage.model.SubscribedShop
import kotlinx.android.synthetic.main.item_subscribed_shop.view.*
import kotlin.properties.Delegates

class SubscribedShopAdapter(
    private val onToggleSubscription: (Boolean, String) -> Unit
) : RecyclerView.Adapter<SubscribedShopAdapter.ShopManageViewHolder>() {

    private var subscribedShopItems: List<SubscribedShop> by Delegates.observable(mutableListOf())
    { _, old, new ->
        autoNotify(old, new) { o, n ->
            o.shopName == n.shopName
        }
    }

    fun replaceAll(subscribedShops: List<SubscribedShop>) {
        subscribedShopItems = subscribedShops
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopManageViewHolder =
        ShopManageViewHolder(
            DataBindingUtil.inflate(
                parent.context.layoutInflater,
                R.layout.item_subscribed_shop,
                parent,
                false
            )
        ).apply {
            binding.root.subscribeButton.setOnClickListener {
                subscribedShopItems[adapterPosition].let {
                    onToggleSubscription(!it.subscribed, it.shopName)
                }
            }
        }

    override fun getItemCount(): Int = subscribedShopItems.size

    override fun onBindViewHolder(holder: ShopManageViewHolder, position: Int) {
        holder.binding.item = subscribedShopItems[position]
    }

    class ShopManageViewHolder(
        val binding: ItemSubscribedShopBinding
    ) : RecyclerView.ViewHolder(binding.root)
}
