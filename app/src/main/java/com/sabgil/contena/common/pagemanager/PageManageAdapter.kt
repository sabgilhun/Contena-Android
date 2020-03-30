package com.sabgil.contena.common.pagemanager

import android.os.Handler
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class PageManageAdapter<ITEM>(
    private val handler: Handler,
    private val loadNextPage: (cursor: Long) -> Unit,
    private val viewHolderInitializer: ViewHolderInitializer
) : RecyclerView.Adapter<PageManagerViewHolder>() {

    private val rawItemList: MutableList<ITEM> = mutableListOf()

    private val items = ItemsWrapper()

    private var cursor: Long = 0

    val dataObserver: (PageHolder<ITEM>) -> Unit = { pageHolder ->
        cursor = pageHolder.cursor

        if (pageHolder.items.isEmpty()) {
            items.addEmptyOrNoMoreItem()
        } else {
            items.addPageHolder(pageHolder)
        }
    }

    fun initialDataLoad() = loadNextPage(cursor)

    fun getItem(position: Int): ITEM? =
        if (rawItemList.isNotEmpty()) rawItemList[position] else null

    abstract fun onCreateItemViewHolder(parent: ViewGroup): PageManagerViewHolder.ItemViewHolder

    abstract fun onBindItemViewHolder(item: ITEM, viewHolder: PageManagerViewHolder.ItemViewHolder)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PageManagerViewHolder = when (viewType) {
        ItemHolder.EMPTY -> PageManagerViewHolder.EmptyViewHolder(
            viewHolderInitializer.emptyViewHolderInit(parent)
        )
        ItemHolder.NO_MORE -> PageManagerViewHolder.NoMoreViewHolder(
            viewHolderInitializer.noMoreViewHolderInit(parent)
        )
        ItemHolder.LOADING -> PageManagerViewHolder.LoadingViewHolder(
            viewHolderInitializer.loadingViewHolderInit(parent)
        )
        ItemHolder.ITEM -> onCreateItemViewHolder(parent)
        else -> throw IllegalArgumentException()
    }

    override fun onBindViewHolder(holder: PageManagerViewHolder, position: Int) {
        val itemHolder = items[position]

        if (itemHolder is ItemHolder.Item && holder is PageManagerViewHolder.ItemViewHolder) {
            if (items.isLastPosition(position)) {
                items.addLoadingItem()
                loadNextPage(cursor)
            }
            onBindItemViewHolder(itemHolder.item, holder)
        }
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int = items[position].viewType

    private inner class ItemsWrapper {

        private val items: MutableList<ItemHolder<ITEM>> = mutableListOf()

        val size: Int
            get() = items.size

        operator fun get(position: Int) = items[position]

        fun addEmptyOrNoMoreItem() {
            if (items.isEmpty()) {
                items.add(ItemHolder.EmptyItem)
                handler.post { this@PageManageAdapter.notifyDataSetChanged() }
            } else {
                items.remove(ItemHolder.LoadingItem)

                val changedBeforeSize = items.size
                items.add(ItemHolder.NoMoreItem)
                handler.post { this@PageManageAdapter.notifyItemChanged(changedBeforeSize) }
            }
        }

        fun addLoadingItem() {
            val changedBeforeSize = items.size
            items.add((ItemHolder.LoadingItem))
            handler.post { this@PageManageAdapter.notifyItemInserted(changedBeforeSize) }
        }

        fun addPageHolder(loadedPageHolder: PageHolder<ITEM>) {
            items.remove(ItemHolder.LoadingItem)

            val changedBeforeSize = items.size
            items.addAll(loadedPageHolder.toItemList())
            handler.post { this@PageManageAdapter.notifyItemInserted(changedBeforeSize) }

            rawItemList.addAll(loadedPageHolder.items)
        }

        fun isLastPosition(position: Int) = position == items.size - 1

        fun PageHolder<ITEM>.toItemList(): List<ItemHolder<ITEM>> =
            this.items.map { ItemHolder.Item(it) }
    }
}