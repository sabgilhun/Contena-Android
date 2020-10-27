package com.sabgil.contena.common.adapter

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


class ViewTypeMap(typeSetups: List<TypeSetup<out BaseItem, out ViewDataBinding>>) {
    private val layoutMap: Map<Class<out BaseItem>, Int>
    private val onBindMap: Map<Class<out BaseItem>, (BaseItem, ViewDataBinding, Int) -> Unit>
    private val onCreateMap: Map<Int, (ViewDataBinding, RecyclerView.ViewHolder) -> Unit>

    init {
        layoutMap = initLayoutMap(typeSetups)
        onBindMap = initOnBindMap(typeSetups)
        onCreateMap = initOnCreateMap(typeSetups)
    }

    private fun initLayoutMap(
        typeSetups: List<TypeSetup<out BaseItem, out ViewDataBinding>>
    ) = hashMapOf(*(typeSetups.map { it.itemClass to it.layoutId }.toTypedArray()))

    private fun initOnBindMap(
        typeSetups: List<TypeSetup<out BaseItem, out ViewDataBinding>>
    ) = hashMapOf(*(typeSetups.map { it.itemClass to it.onBind }.toTypedArray()))

    private fun initOnCreateMap(
        typeSetups: List<TypeSetup<out BaseItem, out ViewDataBinding>>
    ) = hashMapOf(*(typeSetups.map { it.layoutId to it.onCreate }.toTypedArray()))

    fun getLayoutId(itemTypeClass: Class<out BaseItem>) =
        requireNotNull(layoutMap[itemTypeClass])

    fun getOnBindViewHolder(itemTypeClass: Class<out BaseItem>) =
        requireNotNull(onBindMap[itemTypeClass])

    fun getOnCreateViewHolder(@LayoutRes layoutId: Int) =
        requireNotNull(onCreateMap[layoutId])
}