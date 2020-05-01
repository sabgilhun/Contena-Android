package com.sabgil.contena.common.ext

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.ViewPropertyAnimator

fun ViewPropertyAnimator.onAnimationEnd(block: (animator: Animator?) -> Unit) {
    setListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animator: Animator?) {
            block(animator)
        }
    })
}