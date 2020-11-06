package com.sabgil.contena.presenter.home.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.sabgil.contena.common.adapter.MultiViewTypeAdapter
import com.sabgil.contena.common.adapter.ViewTypeMap
import com.sabgil.contena.common.adapter.multiViewType
import com.sabgil.contena.common.adapter.viewType
import com.sabgil.contena.common.ext.addOnPageSelected
import com.sabgil.contena.common.ext.runWithItem
import com.sabgil.contena.databinding.*
import com.sabgil.contena.presenter.home.fragment.NewItemTabFragment
import com.sabgil.contena.presenter.home.model.BasePostItem.*

class PostAdapter(
    context: Context,
    private val handler: NewItemTabFragment.Handler
) : MultiViewTypeAdapter() {

    override val viewTypeMap: ViewTypeMap = multiViewType(context) {
        viewType<PostItem, ItemPostBinding> {
            onCreate { binding, viewHolder ->
                setupViewPager(binding, viewHolder)
            }

            onBind { postItem, binding, _ ->
                with(binding) {
                    val adapter = (itemViewPager.adapter as NewItemsViewPagerAdapter)
                    adapter.replaceAll(postItem.newProductItems)

                    item = postItem
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

    private fun setupViewPager(binding: ItemPostBinding, viewHolder: RecyclerView.ViewHolder) {
        with(binding.itemViewPager) {
            adapter = NewItemsViewPagerAdapter()
            addOnPageSelected {
                viewHolder.runWithItem<PostItem>(items) { item ->
                    item.displayingItemIndex = it
                    binding.pageNoTextView.text = item.pageNumber
                }
            }
        }

        binding.tabLayout.attachToViewPager(binding.itemViewPager)
    }
}
