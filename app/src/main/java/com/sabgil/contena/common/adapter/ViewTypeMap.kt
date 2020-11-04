package com.sabgil.contena.common.adapter

import android.content.Context
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import java.util.*


class ViewTypeMap(
    private val context: Context,
    typeSetups: List<TypeSetup<out BaseItem, out ViewDataBinding>>
) {
    private val layoutMap: Map<Class<out BaseItem>, Int>
    private val onBindMap: Map<Class<out BaseItem>, (BaseItem, ViewDataBinding, Int) -> Unit>
    private val onCreateMap: Map<Int, (ViewDataBinding, RecyclerView.ViewHolder) -> Unit>

    init {
        layoutMap = initLayoutMap(typeSetups)
        onBindMap = initOnBindMap(typeSetups)
        onCreateMap = initOnCreateMap(typeSetups)
    }

    fun getLayoutId(itemTypeClass: Class<out BaseItem>) =
        requireNotNull(layoutMap[itemTypeClass])

    fun getOnBindViewHolder(itemTypeClass: Class<out BaseItem>) =
        requireNotNull(onBindMap[itemTypeClass])

    fun getOnCreateViewHolder(@LayoutRes layoutId: Int) =
        requireNotNull(onCreateMap[layoutId])

    private fun Class<out ViewDataBinding>.toLayoutId(): Int {
        val layoutIdStr = simpleName
            .removeSuffix(bindingClassSuffix)
            .replace(regex, replacement)
            .toLowerCase(Locale.getDefault())

        return context.resources.getIdentifier(layoutIdStr, resourceType, context.packageName)
    }

    private fun initLayoutMap(
        typeSetups: List<TypeSetup<out BaseItem, out ViewDataBinding>>
    ) = hashMapOf(*(typeSetups.map {
        it.itemClass to it.bindingClass.toLayoutId()
    }.toTypedArray()))

    private fun initOnBindMap(
        typeSetups: List<TypeSetup<out BaseItem, out ViewDataBinding>>
    ) = hashMapOf(*(typeSetups.map {
        it.itemClass to it.forceCastedOnBind
    }.toTypedArray()))

    private fun initOnCreateMap(
        typeSetups: List<TypeSetup<out BaseItem, out ViewDataBinding>>
    ) = hashMapOf(*(typeSetups.map {
        it.bindingClass.toLayoutId() to it.forceCastedOnCreate
    }.toTypedArray()))

    companion object {
        private val regex = "([a-z])([A-Z]+)".toRegex()
        private const val replacement = "$1_$2"
        private const val bindingClassSuffix = "Binding"
        private const val resourceType = "layout"
    }
}