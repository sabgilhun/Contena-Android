package com.sabgil.contena.common.ext

import android.app.Activity
import android.content.Intent
import androidx.core.os.bundleOf
import kotlin.reflect.KClass

fun Activity.startWith(target: KClass<out Activity>, vararg pairs: Pair<String, Any?>) {
    startActivity(Intent(this, target.java).apply {
        putExtras(bundleOf(*pairs))
    })
}