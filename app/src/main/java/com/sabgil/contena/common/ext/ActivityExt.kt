package com.sabgil.contena.common.ext

import android.app.Activity
import android.content.Intent
import kotlin.reflect.KClass

fun Activity.startWith(target: KClass<out Activity>, block: Intent.() -> Unit) {
    startActivity(Intent(this, target.java).apply(block))
}