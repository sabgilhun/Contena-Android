package com.sabgil.contena.commons.ext

import android.content.Context
import android.view.LayoutInflater
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

val Context.layoutInflater
    get() = requireNotNull(LayoutInflater.from(this))