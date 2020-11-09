package com.sabgil.contena.presenter.home.adapter

import android.content.Context
import android.graphics.Paint
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.sabgil.contena.R
import com.sabgil.contena.common.adapter.*
import com.sabgil.contena.common.ext.addOnPageSelected
import com.sabgil.contena.common.ext.layoutInflater
import com.sabgil.contena.databinding.ItemBookmarkedPostBinding
import com.sabgil.contena.databinding.ItemPostBinding
import com.sabgil.contena.databinding.PageBookmarkedNewItemBinding
import com.sabgil.contena.presenter.home.fragment.PostBookmarkFragment
import com.sabgil.contena.presenter.home.model.BasePostItem.PostItem
import com.sabgil.contena.presenter.home.model.BookmarkedPostItem

class BookmarkedPostAdapter(
    context: Context,
    private val handler: PostBookmarkFragment.Handler
) : MultiViewTypeAdapter() {
    private val pageNoMap = mutableMapOf<Long, Int>()

    override val viewTypeMap: ViewTypeMap = multiViewType(context) {
        viewType<BookmarkedPostItem, ItemBookmarkedPostBinding> {
            onCreate { binding, _ ->
                with(binding.itemViewPager) {
                    adapter = NewItemsViewPagerAdapter()
                    addOnPageSelected {
                        binding.pageNo = it
                    }
                }

                binding.tabLayout.attachToViewPager(binding.itemViewPager)
            }

            onBind { bookmarkedPostItem, binding, _ ->
                with(binding) {
                    val adapter = (itemViewPager.adapter as NewItemsViewPagerAdapter)
                    adapter.replaceAll(bookmarkedPostItem.newProductItems)

                    val savedPageNo = pageNoMap[bookmarkedPostItem.postId]
                    itemViewPager.currentItem = savedPageNo ?: 0

                    item = bookmarkedPostItem
                    pageNo = savedPageNo
                    handler = this@BookmarkedPostAdapter.handler
                }
            }
        }
    }

    override fun onViewDetachedFromWindow(holder: BindingViewHolder) {
        val adapterPosition = holder.adapterPosition
        if (adapterPosition != RecyclerView.NO_POSITION) {
            val item = items[adapterPosition]
            if (item is PostItem) {
                val binding = holder.binding as ItemPostBinding
                pageNoMap[item.postId] = binding.itemViewPager.currentItem
            }
        }

        super.onViewDetachedFromWindow(holder)
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

    private class NewItemsViewPagerAdapter : PagerAdapter() {

        private val items: MutableList<BookmarkedPostItem.NewProductItem> = mutableListOf()

        fun replaceAll(items: List<BookmarkedPostItem.NewProductItem>) {
            this.items.apply {
                clear()
                addAll(items)
            }
            notifyDataSetChanged()
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean =
            view === `object` as View

        override fun getItemPosition(`object`: Any): Int = POSITION_NONE

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun getCount(): Int = items.size

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val binding = DataBindingUtil.inflate<PageBookmarkedNewItemBinding>(
                container.context.layoutInflater,
                R.layout.page_bookmarked_new_item,
                container,
                true
            )
            // TODO: databinding 으로 빼기
            binding.productOriginPriceTextView.paintFlags =
                binding.productOriginPriceTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            binding.newItem = items[position]
            return binding.root
        }
    }
}
