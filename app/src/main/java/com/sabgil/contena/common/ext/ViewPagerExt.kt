package com.sabgil.contena.common.ext

import androidx.databinding.BindingAdapter
import androidx.viewpager.widget.ViewPager

fun ViewPager.addOnPageSelected(block: (Int) -> Unit) {
    addOnPageChangeListener(object :
        ViewPager.OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        override fun onPageSelected(position: Int) = block(position)

        override fun onPageScrollStateChanged(state: Int) {

        }
    })
}

@BindingAdapter("pageMarginWithDp")
fun ViewPager.setPageMarginWithDp(dp: Float) {
    pageMargin = context.dpToPx(dp).toInt()
}