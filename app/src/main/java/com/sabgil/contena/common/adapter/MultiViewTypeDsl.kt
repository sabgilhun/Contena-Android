package com.sabgil.contena.common.adapter

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding


fun viewTypes(block: ViewTypesSetup.() -> Unit): ViewTypeMap =
    ViewTypesSetup().apply(block).build()


class ViewTypesSetup {
    private val items = mutableListOf<ItemSetup<out BaseItem, out ViewDataBinding>>()

    fun <I : BaseItem, B : ViewDataBinding> item(
        @LayoutRes
        layoutId: Int,
        bindingClass: Class<B>,
        itemClass: Class<I>,
        block: ItemSetup<I, B>.() -> Unit
    ) {
        val itemSetup = ItemSetup(layoutId, bindingClass, itemClass)
        itemSetup.block()
        items.add(itemSetup)
    }

    fun build(): ViewTypeMap {
        return ViewTypeMap(items)
    }
}

class ItemSetup<I : BaseItem, B : ViewDataBinding>(
    @LayoutRes
    val layoutId: Int,
    val bindingClass: Class<B>,
    val itemClass: Class<I>
) {
    var onBindViewHolder: OnBind<I, B> = OnBind { _, _, _ -> }
    var onCreateViewHolder: OnCreate<B> = OnCreate { }

    fun onBindViewHolder(
        onBind: (I, B, Int) -> Unit
    ) {
        this.onBindViewHolder = OnBind(onBind)
    }

    fun onCreateViewHolder(
        onCreate: (B) -> Unit
    ) {
        this.onCreateViewHolder = OnCreate(onCreate)
    }
}

class ViewTypeMap(
    itemSetups: List<ItemSetup<out BaseItem, out ViewDataBinding>>
) {
    private val layoutMap: Map<Class<out BaseItem>, Int>
    private val onBindMap: Map<Class<out BaseItem>, OnBind<BaseItem, ViewDataBinding>>
    private val onCreateMap: Map<Int, OnCreate<ViewDataBinding>>

    init {
        layoutMap = initLayoutMap(itemSetups)
        onBindMap = initOnBindMap(itemSetups)
        onCreateMap = initOnCreateMap(itemSetups)
    }

    fun getLayoutId(itemTypeClass: Class<out BaseItem>): Int =
        requireNotNull(layoutMap[itemTypeClass])

    fun getOnBind(itemTypeClass: Class<out BaseItem>): OnBind<BaseItem, ViewDataBinding> =
        requireNotNull(onBindMap[itemTypeClass])

    fun getOnCreate(@LayoutRes layoutId: Int): OnCreate<ViewDataBinding> =
        requireNotNull(onCreateMap[layoutId])

    private fun initLayoutMap(
        itemSetups: List<ItemSetup<out BaseItem, out ViewDataBinding>>
    ): Map<Class<out BaseItem>, Int> {
        val layoutMap = hashMapOf<Class<out BaseItem>, Int>()

        itemSetups.forEach {
            layoutMap[it.itemClass] = it.layoutId
        }

        return layoutMap
    }

    private fun initOnBindMap(
        itemSetups: List<ItemSetup<out BaseItem, out ViewDataBinding>>
    ): Map<Class<out BaseItem>, OnBind<BaseItem, ViewDataBinding>> {
        val onBindMap = hashMapOf<Class<out BaseItem>, OnBind<BaseItem, ViewDataBinding>>()

        itemSetups.forEach {
            @Suppress("UNCHECKED_CAST")
            onBindMap[it.itemClass] = it.onBindViewHolder as OnBind<BaseItem, ViewDataBinding>
        }

        return onBindMap
    }

    private fun initOnCreateMap(
        itemSetups: List<ItemSetup<out BaseItem, out ViewDataBinding>>
    ): Map<Int, OnCreate<ViewDataBinding>> {
        val onCreateMap = hashMapOf<Int, OnCreate<ViewDataBinding>>()

        itemSetups.forEach {
            @Suppress("UNCHECKED_CAST")
            onCreateMap[it.layoutId] = it.onCreateViewHolder as OnCreate<ViewDataBinding>
        }

        return onCreateMap
    }
}

class OnBind<I : BaseItem, B : ViewDataBinding>(
    val onBind: (I, B, Int) -> Unit
)

class OnCreate<B : ViewDataBinding>(
    val onCreate: (B) -> Unit
)