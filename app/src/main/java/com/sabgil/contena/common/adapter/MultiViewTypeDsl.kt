package com.sabgil.contena.common.adapter

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding

inline fun multiViewType(block: ViewTypesSetup.() -> Unit) =
    ViewTypesSetup().apply(block).build()

inline fun <reified I : BaseItem, reified B : ViewDataBinding> ViewTypesSetup.viewType(
    @LayoutRes
    layoutId: Int,
    block: TypeSetup<I, B>.() -> Unit = {}
) {
    items.add(TypeSetup<I, B>(layoutId, I::class.java).apply(block))
}

class ViewTypesSetup {
    val items = mutableListOf<TypeSetup<out BaseItem, out ViewDataBinding>>()

    fun build(): ViewTypeMap {
        return ViewTypeMap(items)
    }
}

fun <I : BaseItem, B : ViewDataBinding> TypeSetup<I, B>.onBind(
    onBind: (I, B, Int) -> Unit
) = setOnBind(onBind)

fun <I : BaseItem, B : ViewDataBinding> TypeSetup<I, B>.onCreate(
    onCreate: (B, () -> I?) -> Unit
) = setOnCreate(onCreate)

class TypeSetup<I : BaseItem, B : ViewDataBinding>(
    @LayoutRes
    val layoutId: Int,
    val itemClass: Class<I>
) {
    private var _onBind: (I, B, Int) -> Unit = { _, _, _ -> }
    private var _onCreate: (B, () -> I?) -> Unit = { _, _ -> }

    val onBind
        @Suppress("UNCHECKED_CAST")
        get() = _onBind as (BaseItem, ViewDataBinding, Int) -> Unit

    val onCreate
        @Suppress("UNCHECKED_CAST")
        get() = _onCreate as (ViewDataBinding, () -> BaseItem?) -> Unit

    fun setOnBind(onBind: (I, B, Int) -> Unit) {
        this._onBind = onBind
    }

    fun setOnCreate(onCreate: (B, () -> I?) -> Unit) {
        this._onCreate = onCreate
    }
}
