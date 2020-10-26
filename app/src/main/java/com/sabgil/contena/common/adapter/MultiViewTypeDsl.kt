package com.sabgil.contena.common.adapter

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding

fun viewTypes(block: ViewTypesSetup.() -> Unit): ViewTypeMap =
    ViewTypesSetup().apply(block).build()

class ViewTypesSetup {
    private val items = mutableListOf<TypeSetup<out BaseItem, out ViewDataBinding>>()

    fun <I : BaseItem, B : ViewDataBinding> viewType(
        @LayoutRes
        layoutId: Int,
        @Suppress("UNUSED_PARAMETER")
        bindingClass: Class<B>,
        itemClass: Class<I>,
        block: TypeSetup<I, B>.() -> Unit = {}
    ) {
        items.add(TypeSetup<I, B>(layoutId, itemClass).apply(block))
    }

    fun build(): ViewTypeMap {
        return ViewTypeMap(items)
    }
}

class TypeSetup<I : BaseItem, B : ViewDataBinding>(
    @LayoutRes
    val layoutId: Int,
    val itemClass: Class<I>
) {
    var onBindViewHolder: (I, B, Int) -> Unit = { _, _, _ -> }
    var onCreateViewHolder: (B) -> Unit = {}

    fun onBindViewHolder(
        onBind: (I, B, Int) -> Unit
    ) {
        this.onBindViewHolder = onBind
    }

    fun onCreateViewHolder(
        onCreate: (B) -> Unit
    ) {
        this.onCreateViewHolder = onCreate
    }
}
