package com.sabgil.contena.common.adapter.dsl

import android.content.Context
import androidx.databinding.ViewDataBinding
import com.sabgil.contena.common.adapter.BaseItem
import com.sabgil.contena.common.adapter.ViewTypeMapStore

class MultiViewTypeScope(private val context: Context) {
    private val viewTypeScopes = mutableListOf<ViewTypeScope<out BaseItem, out ViewDataBinding>>()

    fun addType(type: ViewTypeScope<out BaseItem, out ViewDataBinding>) {
        viewTypeScopes.add(type)
    }

    fun viewTypeMapStoreBuild() = ViewTypeMapStore.Builder(context, viewTypeScopes).build()
}