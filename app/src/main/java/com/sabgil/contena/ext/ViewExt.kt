package com.sabgil.contena.ext

import android.view.View

fun View.setVisiblity(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}