package com.sabgil.contena.common.adapter

import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.sabgil.contena.common.adapter.dsl.MultiViewTypeScope
import com.sabgil.contena.common.adapter.dsl.ViewTypeScope

inline fun Context.viewTypeMapStore(block: MultiViewTypeScope.() -> Unit): ViewTypeMapStore {
    return MultiViewTypeScope(this).apply(block).viewTypeMapStoreBuild()
}

inline fun Context.multiViewTypeAdapter(block: MultiViewTypeScope.() -> Unit): MultiViewTypeAdapter {
    val viewTypeMapStore = MultiViewTypeScope(this).apply(block).viewTypeMapStoreBuild()
    return object : MultiViewTypeAdapter() {
        override val viewTypeMapStore: ViewTypeMapStore
            get() = viewTypeMapStore
    }
}

inline fun <reified I : BaseItem, reified B : ViewDataBinding> MultiViewTypeScope.type(
    block: ViewTypeScope<I, B>.() -> Unit = {}
) {
    addType(ViewTypeScope(I::class.java, B::class.java).apply(block))
}