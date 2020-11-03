package com.sabgil.contena.presenter.home.adapter

import androidx.core.view.isInvisible
import com.sabgil.contena.R
import com.sabgil.contena.common.adapter.*
import com.sabgil.contena.common.ext.addOnPageSelected
import com.sabgil.contena.common.ext.dpToPx
import com.sabgil.contena.databinding.*
import com.sabgil.contena.presenter.home.fragment.NewItemTabFragment
import com.sabgil.contena.presenter.home.model.BasePostItem.*

class PostAdapter(
    private val handler: NewItemTabFragment.Handler
) : MultiViewTypeAdapter() {

    override val viewTypeMap: ViewTypeMap =
        multiViewType {
            viewType<PostItem, ItemPostBinding>(R.layout.item_post) {
                onCreate { binding, viewHolder ->
                    binding.itemViewPager.apply {
                        val dp14 = context.dpToPx(14f).toInt()
                        clipToPadding = false
                        pageMargin = dp14
                        setPadding(dp14, 0, dp14, 0)
                        adapter = NewItemsViewPagerAdapter()
                    }
                    binding.tabLayout.attachToViewPager(binding.itemViewPager)
                    binding.itemViewPager.addOnPageSelected {
                        val adapterPosition = viewHolder.adapterPosition
                        if (adapterPosition != -1) {
                            (items[adapterPosition] as PostItem).displayingItemIndex = it
                        }
                    }
                }

                onBind { postItem, binding, _ ->
                    with(binding) {
                        item = postItem
                        (itemViewPager.adapter as NewItemsViewPagerAdapter).replaceAll(postItem.newProductItems)
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

            viewType<EmptyItem, ItemPostEmptyBinding>(R.layout.item_post_empty) {
                onCreate { binding, _ ->
                    binding.goToSubscribeTabButton.setOnClickListener { handler.goToSearchTab() }
                }
            }

            viewType<NoMoreItem, ItemPostNoMoreBinding>(R.layout.item_post_no_more)

            viewType<LoadFailItem, ItemLoadFailBinding>(R.layout.item_load_fail) {
                onCreate { binding, viewHolder ->
                    binding.reloadMorePostButton.setOnClickListener {
                        val adapterPosition = viewHolder.adapterPosition
                        if (adapterPosition != -1) {
                            handler.loadMorePost((items[adapterPosition] as LoadFailItem).cursor)
                            binding.reloadMorePostButton.isInvisible = true
                            binding.reloadLoadingProgressBar.isInvisible = false
                        }
                    }
                }
            }
        }
}
