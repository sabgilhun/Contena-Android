package com.sabgil.contena.presenter.home.adapter

import android.content.Context
import com.sabgil.contena.common.adapter.BaseItem
import com.sabgil.contena.common.adapter.MultiViewTypeAdapter
import com.sabgil.contena.common.adapter.type
import com.sabgil.contena.common.adapter.viewTypeMapStore
import com.sabgil.contena.common.ext.addOnPageSelected
import com.sabgil.contena.databinding.*
import com.sabgil.contena.presenter.home.fragment.NewItemTabFragment
import com.sabgil.contena.presenter.home.model.BasePostItem.*

class PostAdapter(
    context: Context,
    private val handler: NewItemTabFragment.Handler
) : MultiViewTypeAdapter() {
    private val pageNoMap = mutableMapOf<Long, Int>()

    override val viewTypeMapStore = context.viewTypeMapStore {
        type<PostItem, ItemPostBinding> {
            onCreate { binding, _, itemSupplier ->
                with(binding.itemViewPager) {
                    adapter = NewItemsViewPagerAdapter()
                    addOnPageSelected {
                        binding.pageNo = it
                        itemSupplier()?.let { item -> pageNoMap[item.postId] = currentItem }
                    }
                }

                binding.tabLayout.attachToViewPager(binding.itemViewPager)
            }

            onBind { postItem, binding ->
                with(binding) {
                    val adapter = (itemViewPager.adapter as NewItemsViewPagerAdapter)
                    adapter.replaceAll(postItem.newProductItems)

                    val savedPageNo = pageNoMap[postItem.postId]
                    itemViewPager.currentItem = savedPageNo ?: 0

                    item = postItem
                    pageNo = savedPageNo
                    handler = this@PostAdapter.handler
                }
            }
        }

        type<LoadingItem, ItemPostLoadingBinding> {
            onBind { loadingItem ->
                if (!loadingItem.statedLoading.getAndSet(true)) {
                    handler.loadMorePost(loadingItem.nextCursor)
                }
            }
        }

        type<EmptyItem, ItemPostEmptyBinding> {
            onBind { _, binding ->
                binding.handler = handler
            }
        }

        type<NoMoreItem, ItemPostNoMoreBinding>()

        type<MoreLoadFailItem, ItemMoreLoadFailBinding> {
            onBind { moreLoadFailItem, binding ->
                with(binding) {
                    item = moreLoadFailItem
                    handler = this@PostAdapter.handler
                }
            }
        }
    }

    override fun update(items: List<BaseItem>) {
        val newItemIds = items.filterIsInstance(PostItem::class.java).map {
            it.postId
        }

        newItemIds.forEach {
            if (pageNoMap[it] == null) {
                pageNoMap[it] = 0
            }
        }

        super.update(items)
    }
}
