package com.sabgil.contena.presenter.home.adapter.post

import androidx.viewpager.widget.ViewPager


class PageChangeObserver : ViewPager.OnPageChangeListener {
    private var observer: ((Int) -> Unit)? = null

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(
        position: Int,
        positionOffset: Float,
        positionOffsetPixels: Int
    ) {
    }

    override fun onPageSelected(position: Int) {
        observer?.invoke(position)
    }

    fun registerObserver(observer: (Int) -> Unit) {
        this.observer = observer
    }

    fun unRegisterObserver() {
        observer = null
    }
}