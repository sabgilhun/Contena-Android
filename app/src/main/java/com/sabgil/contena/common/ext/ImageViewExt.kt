package com.sabgil.contena.common.ext

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageWithCircleCrop")
fun ImageView.setImageWithCircleCrop(uri: String?) {
    if (!uri.isNullOrEmpty()) {
        Glide.with(this)
            .load(uri)
            .circleCrop()
            .into(this)
    }
}

@BindingAdapter("image")
fun ImageView.setImage(uri: String?) {
    if (!uri.isNullOrEmpty()) {
        Glide.with(this)
            .load(uri)
            .into(this)
    }
}