package com.sabgil.contena.presenter.home.adapter

import android.content.Context
import androidx.core.view.isInvisible
import androidx.viewpager.widget.ViewPager
import com.sabgil.contena.common.adapter.*
import com.sabgil.contena.common.ext.addOnPageSelected
import com.sabgil.contena.common.ext.dpToPx
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
                binding.itemViewPager.apply {
                    adapter = NewItemsViewPagerAdapter()
                    pageMarginSetup()
                    addOnPageSelected {
                        val adapterPosition = viewHolder.adapterPosition
                        if (adapterPosition != -1) {
                            val postItem = (items[adapterPosition] as PostItem)
                            postItem.displayingItemIndex = it
                            binding.pageNoTextView.text = postItem.pageNumber
                        }
                    }
                }

                binding.goToDetailImageButton.setOnClickListener {
                    val adapterPosition = viewHolder.adapterPosition
                    if (adapterPosition != -1) {
                        val postItem = (items[adapterPosition] as PostItem)
                        this@PostAdapter.handler.goToPostDetailActivity(
                            postItem.postId,
                            postItem.uploadDate
                        )
                    }
                }

                binding.tabLayout.attachToViewPager(binding.itemViewPager)
            }

            onBind { postItem, binding, _ ->
                with(binding) {
                    item = postItem
                    (itemViewPager.adapter as NewItemsViewPagerAdapter).replaceAll(postItem.newProductItems)
                    itemViewPager.currentItem = postItem.displayingItemIndex
                    pageNoTextView.text = postItem.pageNumber
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

    private fun ViewPager.pageMarginSetup() {
        val dp14 = context.dpToPx(14f).toInt()
        clipToPadding = false
        pageMargin = dp14
        setPadding(dp14, 0, dp14, 0)
    }
}
