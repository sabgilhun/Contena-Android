package com.sabgil.contena.common.pagemanager

sealed class ItemHolder<out ITEM>(val viewType: Int) {

    object EmptyItem : ItemHolder<Nothing>(EMPTY)

    object NoMoreItem : ItemHolder<Nothing>(NO_MORE)

    object LoadingItem : ItemHolder<Nothing>(LOADING)

    class Item<ITEM>(val item: ITEM) : ItemHolder<ITEM>(ITEM)

    companion object {
        const val EMPTY: Int = 1
        const val NO_MORE: Int = 2
        const val LOADING: Int = 3
        const val ITEM: Int = 4
    }
}
