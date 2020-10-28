package com.sabgil.contena.presenter.home.adapter

import com.sabgil.contena.R
import com.sabgil.contena.common.adapter.*
import com.sabgil.contena.common.ext.setBarColor
import com.sabgil.contena.common.ext.visibleOrGone
import com.sabgil.contena.databinding.ItemSearchedShopBinding
import com.sabgil.contena.presenter.home.fragment.SearchTabFragment
import com.sabgil.contena.presenter.home.model.SearchedShop

class SearchedShopAdapter(
    private val handler: SearchTabFragment.Handler
) : MultiViewTypeAdapter() {
    override val viewTypeMap: ViewTypeMap =
        multiViewType {
            viewType<SearchedShop, ItemSearchedShopBinding>(R.layout.item_searched_shop) {
                onCreate { binding, viewHolder ->
                    binding.subscribeButton.setOnClickListener {
                        val position = viewHolder.adapterPosition
                        if (position == -1) return@setOnClickListener
                        toggleSubscribeButton(binding, (items[position] as SearchedShop))
                    }
                }

                onBind { searchedShop, binding, _ ->
                    binding.item = searchedShop
                }
            }
        }

    private fun toggleSubscribeButton(
        binding: ItemSearchedShopBinding,
        item: SearchedShop
    ) {
        if (!item.isLoading) {
            item.isLoading = true
            binding.subscribeLoadingBar.setBarColor(item.loadingBarColor)
            binding.subscribeLoadingBar.visibleOrGone = true
            handler.toggleSubscription(item)
        }
    }
}