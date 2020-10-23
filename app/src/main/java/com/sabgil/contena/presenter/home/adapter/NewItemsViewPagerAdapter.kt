package com.sabgil.contena.presenter.home.adapter

import android.graphics.Paint
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.PagerAdapter
import com.sabgil.contena.R
import com.sabgil.contena.common.ext.layoutInflater
import com.sabgil.contena.databinding.PageNewItemBinding
import com.sabgil.contena.domain.model.Post

class NewItemsViewPagerAdapter : PagerAdapter() {

    private val items: MutableList<Post.NewItem> = mutableListOf()

    fun replaceAll(items: List<Post.NewItem>) {
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
        val binding = DataBindingUtil.inflate<PageNewItemBinding>(
            container.context.layoutInflater,
            R.layout.page_new_item,
            container,
            true
        )
        // TODO: databinding 으로 빼기
        binding.productOriginPriceTextView.paintFlags =
            binding.productOriginPriceTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        binding.newItem = items[position]
        return binding.root
    }
}