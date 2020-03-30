package com.sabgil.contena.common.pagemanager

import android.view.View
import androidx.recyclerview.widget.RecyclerView

sealed class PageManagerViewHolder(view: View) :
    RecyclerView.ViewHolder(view) {

    class EmptyViewHolder(view: View) : PageManagerViewHolder(view)

    class NoMoreViewHolder(view: View) : PageManagerViewHolder(view)

    class LoadingViewHolder(view: View) : PageManagerViewHolder(view)

    abstract class ItemViewHolder(view: View) : PageManagerViewHolder(view)
}