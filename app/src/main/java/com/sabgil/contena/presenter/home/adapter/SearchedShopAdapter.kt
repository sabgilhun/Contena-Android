package com.sabgil.contena.presenter.home.adapter

import com.sabgil.contena.R
import com.sabgil.contena.common.adapter.*
import com.sabgil.contena.common.ext.setBarColor
import com.sabgil.contena.common.ext.visibleOrGone
import com.sabgil.contena.databinding.ItemSearchedShopBinding
import com.sabgil.contena.databinding.ItemSearchedShopEmptyBinding
import com.sabgil.contena.presenter.home.fragment.SearchTabFragment
import com.sabgil.contena.presenter.home.model.SearchedShopItem.Empty
import com.sabgil.contena.presenter.home.model.SearchedShopItem.Shop

class SearchedShopAdapter(
    private val handler: SearchTabFragment.Handler
) : MultiViewTypeAdapter() {
    override val viewTypeMap: ViewTypeMap =
        multiViewType {
            viewType<Shop, ItemSearchedShopBinding>(R.layout.item_searched_shop) {
                onCreate { binding, viewHolder ->
                    binding.subscribeButton.setOnClickListener {
                        val position = viewHolder.adapterPosition
                        if (position == -1) return@setOnClickListener
                        toggleSubscribeButton(binding, (items[position] as Shop))
                    }
                }

                onBind { searchedShop, binding, _ ->
                    binding.item = searchedShop
                }
            }

            viewType<Empty, ItemSearchedShopEmptyBinding>(R.layout.item_searched_shop_empty)
        }

    private fun toggleSubscribeButton(
        binding: ItemSearchedShopBinding,
        item: Shop
    ) {
        if (!item.isLoading) {
            item.isLoading = true
            binding.subscribeLoadingBar.setBarColor(item.loadingBarColor)
            binding.subscribeLoadingBar.visibleOrGone = true
            handler.toggleSubscription(item)
        }
    }
}