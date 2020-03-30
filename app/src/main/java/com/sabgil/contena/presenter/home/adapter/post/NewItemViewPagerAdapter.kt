package com.sabgil.contena.presenter.home.adapter.post

import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.PagerAdapter
import com.sabgil.contena.R
import com.sabgil.contena.common.ext.layoutInflater
import com.sabgil.contena.databinding.PagePostImageBinding
import com.sabgil.contena.domain.model.SummaryNewItem

class NewItemViewPagerAdapter : PagerAdapter() {

    private val items: MutableList<SummaryNewItem> = mutableListOf()

    fun replaceAll(items: List<SummaryNewItem>) {
        this.items.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view === `object` as View

    override fun getItemPosition(`object`: Any): Int = POSITION_NONE

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int = items.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding = DataBindingUtil.inflate<PagePostImageBinding>(
            container.context.layoutInflater,
            R.layout.page_post_image,
            container,
            true
        )
        binding.summaryNewItem = items[position]
        return binding.root
    }
}