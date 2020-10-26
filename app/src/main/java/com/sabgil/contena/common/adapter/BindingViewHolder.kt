package com.sabgil.contena.common.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BindingViewHolder(
    val binding: ViewDataBinding
) : RecyclerView.ViewHolder(binding.root)

open class BaseItem()