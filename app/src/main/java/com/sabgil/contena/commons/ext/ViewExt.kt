package com.sabgil.contena.commons.ext

import android.content.Context
import android.view.LayoutInflater

val Context.layoutInflater
    get() = requireNotNull(LayoutInflater.from(this))
