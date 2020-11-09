package com.sabgil.contena.presenter.home.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.sabgil.contena.common.adapter.*
import com.sabgil.contena.common.ext.addOnPageSelected
import com.sabgil.contena.databinding.*
import com.sabgil.contena.presenter.home.fragment.NewItemTabFragment
import com.sabgil.contena.presenter.home.model.BasePostItem.*

class PostAdapter(
    context: Context,
    private val handler: NewItemTabFragment.Handler
) : MultiViewTypeAdapter() {
    private val pageNoMap = mutableMapOf<Long, Int>()

    override val viewTypeMap: ViewTypeMap = multiViewType(context) {
        viewType<PostItem, ItemPostBinding> {
            onCreate { binding, holder ->
                with(binding.itemViewPager) {
                    adapter = NewItemsViewPagerAdapter()
                    addOnPageSelected {
                        binding.pageNo = it
                        val adapterPosition = holder.adapterPosition
                        if (adapterPosition != RecyclerView.NO_POSITION) {
                            val item = items[adapterPosition] as PostItem
                            pageNoMap[item.postId] = currentItem
                        }
                    }
                }

                binding.tabLayout.attachToViewPager(binding.itemViewPager)
            }

            onBind { postItem, binding, _ ->
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

        viewType<LoadingItem, ItemPostLoadingBinding> {
            onBind { loadingItem, _, _ ->
                if (!loadingItem.statedLoading.getAndSet(true)) {
                    handler.loadMorePost(loadingItem.nextCursor)
                }
            }
        }

        viewType<EmptyItem, ItemPostEmptyBinding> {
            onBind { _, binding, _ ->
                binding.handler = handler
            }
        }

        viewType<NoMoreItem, ItemPostNoMoreBinding>()

        viewType<MoreLoadFailItem, ItemMoreLoadFailBinding> {
            onBind { moreLoadFailItem, binding, _ ->
                with(binding) {
                    item = moreLoadFailItem
                    handler = this@PostAdapter.handler
                }
            }
        }
    }

    override fun replaceAll(items: List<BaseItem>) {
        val newItemIds = items.filterIsInstance(PostItem::class.java).map {
            it.postId
        }

        newItemIds.forEach {
            if (pageNoMap[it] == null) {
                pageNoMap[it] = 0
            }
        }

        super.replaceAll(items)
    }
}
