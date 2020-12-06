package com.sabgil.contena.common.adapter.dsl

import com.sabgil.contena.common.adapter.BaseItem
import com.sabgil.contena.common.adapter.BindingViewHolder

interface OnCreateInvoker {

    fun invokeOnCreate(viewHolder: BindingViewHolder, items: List<BaseItem>)

    companion object EmptyInvoker : OnCreateInvoker {
        override fun invokeOnCreate(viewHolder: BindingViewHolder, items: List<BaseItem>) {
            // Empty operation
        }
    }
}