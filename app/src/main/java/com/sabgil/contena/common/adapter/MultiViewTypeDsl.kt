package com.sabgil.contena.common.adapter

import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

inline fun multiViewType(context: Context, block: ViewTypesSetup.() -> Unit) =
    ViewTypesSetup(context).apply(block).build()

inline fun <reified I : BaseItem, reified B : ViewDataBinding> ViewTypesSetup.viewType(
    block: TypeSetup<I, B>.() -> Unit = {}
) {
    types.add(TypeSetup(I::class.java, B::class.java).apply(block))
}

class ViewTypesSetup(
    private val context: Context
) {
    val types = mutableListOf<TypeSetup<out BaseItem, out ViewDataBinding>>()

    fun build(): ViewTypeMap {
        return ViewTypeMap(context, types)
    }
}

class TypeSetup<I : BaseItem, B : ViewDataBinding>(
    val itemClass: Class<I>,
    val bindingClass: Class<B>
) {
    private var _onBind: (I, B, Int) -> Unit = { _, _, _ -> }
    private var _onCreate: (B, RecyclerView.ViewHolder) -> Unit = { _, _ -> }

    fun onBind(onBind: (I, B, Int) -> Unit) {
        _onBind = onBind
    }

    fun onCreate(onCreate: (B, RecyclerView.ViewHolder) -> Unit) {
        _onCreate = onCreate
    }

    val forceCastedOnBind
        @Suppress("UNCHECKED_CAST")
        get() = _onBind as (BaseItem, ViewDataBinding, Int) -> Unit

    val forceCastedOnCreate
        @Suppress("UNCHECKED_CAST")
        get() = _onCreate as (ViewDataBinding, RecyclerView.ViewHolder) -> Unit
}