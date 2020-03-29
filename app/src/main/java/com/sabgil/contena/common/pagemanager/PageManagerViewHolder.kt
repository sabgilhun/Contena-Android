package com.sabgil.contena.common.pagemanager

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

sealed class PageManagerViewHolder<out B : ViewDataBinding>(view: View) :
    RecyclerView.ViewHolder(view) {

    class EmptyViewHolder(view: View) : PageManagerViewHolder<Nothing>(view)

    class NoMoreViewHolder(view: View) : PageManagerViewHolder<Nothing>(view)

    class LoadingViewHolder(view: View) : PageManagerViewHolder<Nothing>(view)

    class ItemViewHolder<B : ViewDataBinding>(val binding: B) :
        PageManagerViewHolder<B>(binding.root)
}