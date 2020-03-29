package com.sabgil.contena.common.ext

import android.view.View

fun View.setVisible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.setGone(isGone: Boolean) {
    visibility = if (isGone) View.GONE else View.VISIBLE
}