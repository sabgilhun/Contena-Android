package com.sabgil.contena.common.ext

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageWithCircleCrop")
fun setImageWithCircleCrop(
    imageView: ImageView,
    uri: String?
) {
    if (!uri.isNullOrEmpty()) {
        Glide.with(imageView)
            .load(uri)
            .circleCrop()
            .into(imageView)
    }
}

@BindingAdapter("image")
fun setImage(
    imageView: ImageView,
    uri: String?
) {
    if (!uri.isNullOrEmpty()) {
        Glide.with(imageView)
            .load(uri)
            .into(imageView)
    }
}