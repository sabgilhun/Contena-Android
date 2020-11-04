package com.sabgil.contena.presenter.home.adapter

import android.content.Context
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.sabgil.contena.common.adapter.*
import com.sabgil.contena.common.ext.addOnPageSelected
import com.sabgil.contena.common.ext.dpToPx
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

                binding.goToDetailImageButton.setOnClickListener {
                    viewHolder.runWithItem<PostItem>(items) { item ->
                        handler.goToPostDetailActivity(item.postId, item.uploadDate)
                    }
                }
            }

            onBind { postItem, binding, _ ->
                with(binding) {
                    val adapter = (itemViewPager.adapter as NewItemsViewPagerAdapter)
                    adapter.replaceAll(postItem.newProductItems)
                    item = postItem
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
            onCreate { binding, _ ->
                binding.goToSubscribeTabButton.setOnClickListener { handler.goToSearchTab() }
            }
        }

        viewType<NoMoreItem, ItemPostNoMoreBinding>()

        viewType<LoadFailItem, ItemLoadFailBinding> {
            onCreate { binding, viewHolder ->
                binding.reloadMorePostButton.setOnClickListener {
                    viewHolder.runWithItem<LoadFailItem>(items) { item ->
                        handler.loadMorePost(item.cursor)
                        binding.reloadMorePostButton.isInvisible = true
                        binding.reloadLoadingProgressBar.isInvisible = false
                    }
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
