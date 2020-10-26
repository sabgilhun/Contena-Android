package com.sabgil.contena.common.adapter

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding


class ViewTypeMap(typeSetups: List<TypeSetup<out BaseItem, out ViewDataBinding>>) {
    private val layoutMap: Map<Class<out BaseItem>, Int>
    private val onBindMap: Map<Class<out BaseItem>, (BaseItem, ViewDataBinding, Int) -> Unit>
    private val onCreateMap: Map<Int, (ViewDataBinding) -> Unit>

    init {
        layoutMap = initLayoutMap(typeSetups)
        onBindMap = initOnBindMap(typeSetups)
        onCreateMap = initOnCreateMap(typeSetups)
    }

    private fun initLayoutMap(
        typeSetups: List<TypeSetup<out BaseItem, out ViewDataBinding>>
    ): Map<Class<out BaseItem>, Int> {
        val layoutMap = hashMapOf<Class<out BaseItem>, Int>()

        typeSetups.forEach {
            layoutMap[it.itemClass] = it.layoutId
        }

        return layoutMap
    }

    private fun initOnBindMap(
        typeSetups: List<TypeSetup<out BaseItem, out ViewDataBinding>>
    ): Map<Class<out BaseItem>, (BaseItem, ViewDataBinding, Int) -> Unit> {
        val onBindMap = hashMapOf<Class<out BaseItem>, (BaseItem, ViewDataBinding, Int) -> Unit>()

        typeSetups.forEach {
            @Suppress("UNCHECKED_CAST")
            onBindMap[it.itemClass] =
                it.onBindViewHolder as (BaseItem, ViewDataBinding, Int) -> Unit
        }

        return onBindMap
    }

    private fun initOnCreateMap(
        typeSetups: List<TypeSetup<out BaseItem, out ViewDataBinding>>
    ): Map<Int, (ViewDataBinding) -> Unit> {
        val onCreateMap = hashMapOf<Int, (ViewDataBinding) -> Unit>()

        typeSetups.forEach {
            @Suppress("UNCHECKED_CAST")
            onCreateMap[it.layoutId] = it.onCreateViewHolder as (ViewDataBinding) -> Unit
        }

        return onCreateMap
    }

    fun getLayoutId(itemTypeClass: Class<out BaseItem>): Int =
        requireNotNull(layoutMap[itemTypeClass])

    fun getOnBindViewHolder(itemTypeClass: Class<out BaseItem>): (BaseItem, ViewDataBinding, Int) -> Unit =
        requireNotNull(onBindMap[itemTypeClass])

    fun getOnCreateViewHolder(@LayoutRes layoutId: Int): (ViewDataBinding) -> Unit =
        requireNotNull(onCreateMap[layoutId])
}