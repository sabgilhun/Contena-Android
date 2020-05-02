package com.sabgil.contena.common.ext

import android.view.View
import android.view.View.*
import androidx.databinding.BindingAdapter

@set:BindingAdapter("visibleOrGone")
var View.visibleOrGone
    get() = visibility == VISIBLE
    set(value) {
        visibility = if (value) VISIBLE else GONE
    }

@set:BindingAdapter("visible")
var View.visible
    get() = visibility == VISIBLE
    set(value) {
        visibility = if (value) VISIBLE else INVISIBLE
    }

@set:BindingAdapter("selected")
var View.selected
    get() = isSelected
    set(value) {
        isSelected = value
    }