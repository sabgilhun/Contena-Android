package com.sabgil.contena.presenter.home.adapter

import android.content.Context
import com.sabgil.contena.R
import com.sabgil.contena.common.adapter.*
import com.sabgil.contena.common.ext.setBarColor
import com.sabgil.contena.common.ext.visibleOrGone
import com.sabgil.contena.databinding.ItemSearchedShopBinding
import com.sabgil.contena.databinding.ItemSearchedShopEmptyBinding
import com.sabgil.contena.presenter.home.fragment.SearchTabFragment
import com.sabgil.contena.presenter.home.model.BaseSearchedShopItem.EmptyItem
import com.sabgil.contena.presenter.home.model.BaseSearchedShopItem.ShopItem

class SearchedShopAdapter(
    context: Context,
    private val handler: SearchTabFragment.Handler
) : MultiViewTypeAdapter() {
    override val viewTypeMapStore = context.viewTypeMapStore {
        type<ShopItem, ItemSearchedShopBinding> {
            onCreate { binding, viewHolder ->
                binding.subscribeButton.setOnClickListener {
                    val position = viewHolder.adapterPosition
                    if (position == -1) return@setOnClickListener
                    toggleSubscribeButton(binding, (items[position] as ShopItem))
                }
            }

            onBind { searchedShop, binding ->
                binding.item = searchedShop
            }
        }

        type<EmptyItem, ItemSearchedShopEmptyBinding>()
    }

    private fun toggleSubscribeButton(
        binding: ItemSearchedShopBinding,
        item: ShopItem
    ) {
        if (!item.isLoading) {
            item.isLoading = true
            binding.subscribeLoadingBar.setBarColor(item.loadingBarColor)
            binding.subscribeLoadingBar.visibleOrGone = true
            handler.toggleSubscription(item)
        }
    }
}