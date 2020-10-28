package com.sabgil.contena.presenter.home.adapter

import com.sabgil.contena.R
import com.sabgil.contena.common.adapter.*
import com.sabgil.contena.common.ext.addOnPageSelected
import com.sabgil.contena.databinding.ItemPostBinding
import com.sabgil.contena.databinding.ItemPostEmptyBinding
import com.sabgil.contena.databinding.ItemPostLoadingBinding
import com.sabgil.contena.databinding.ItemPostNoMoreBinding
import com.sabgil.contena.presenter.home.fragment.NewItemTabFragment
import com.sabgil.contena.presenter.home.model.EmptyItem
import com.sabgil.contena.presenter.home.model.LoadingItem
import com.sabgil.contena.presenter.home.model.NoMoreItem
import com.sabgil.contena.presenter.home.model.PostItem

class PostAdapter(
    private val handler: NewItemTabFragment.Handler
) : MultiViewTypeAdapter() {

    override val viewTypeMap: ViewTypeMap =
        multiViewType {
            viewType<PostItem, ItemPostBinding>(R.layout.item_post) {
                onCreate { binding, boundItemSupplier ->
                    // TODO: dp 변환 작업 필요
                    binding.itemViewPager.apply {
                        clipToPadding = false
                        pageMargin = 28
                        setPadding(28, 0, 28, 0)
                        adapter = NewItemsViewPagerAdapter()
                    }
                    binding.tabLayout.attachToViewPager(binding.itemViewPager)
                    binding.itemViewPager.addOnPageSelected {
                        val postItem = boundItemSupplier()
                        postItem?.apply {
                            displayingItemIndex = it
                        }
                    }
                }

                onBind { postItem, binding, _ ->
                    with(binding) {
                        item = postItem
                        (itemViewPager.adapter as NewItemsViewPagerAdapter).replaceAll(postItem.newItemList)
                        itemViewPager.currentItem = postItem.displayingItemIndex
                    }
                }
            }

            viewType<LoadingItem, ItemPostLoadingBinding>(R.layout.item_post_loading) {
                onBind { loadingItem, _, _ ->
                    if (!loadingItem.statedLoading.getAndSet(true)) {
                        handler.loadMorePost(loadingItem.nextCursor)
                    }
                }
            }

            viewType<EmptyItem, ItemPostEmptyBinding>(R.layout.item_post_empty)
            viewType<NoMoreItem, ItemPostNoMoreBinding>(R.layout.item_post_no_more)
        }
}
