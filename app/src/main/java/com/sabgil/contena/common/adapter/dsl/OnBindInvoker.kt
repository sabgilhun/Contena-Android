package com.sabgil.contena.common.adapter.dsl

import com.sabgil.contena.common.adapter.BaseItem
import com.sabgil.contena.common.adapter.BindingViewHolder

interface OnBindInvoker {

    fun invokeOnBind(item: BaseItem, viewHolder: BindingViewHolder, position: Int)

    companion object EmptyInvoker : OnBindInvoker {
        override fun invokeOnBind(item: BaseItem, viewHolder: BindingViewHolder, position: Int) {
            // Empty operation
        }
    }
}