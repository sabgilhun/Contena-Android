package com.sabgil.contena.presenter.home.adapter

import android.os.Handler
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener
import com.sabgil.contena.R
import com.sabgil.contena.common.ext.layoutInflater
import com.sabgil.contena.common.pagemanager.PageManageAdapter
import com.sabgil.contena.common.pagemanager.ViewHolderInitializer
import com.sabgil.contena.databinding.ItemPostBinding
import com.sabgil.contena.databinding.ItemPostEmptyBinding
import com.sabgil.contena.databinding.ItemPostLoadingBinding
import com.sabgil.contena.databinding.ItemPostNoMoreBinding
import com.sabgil.contena.presenter.home.model.PostListItem

class PostAdapter(
    handler: Handler,
    loadMoreData: (Long) -> Unit
) : PageManageAdapter<PostListItem, ItemPostBinding>(
    handler,
    loadMoreData,
    ViewHolderInitializerImpl()
) {
    override fun onBindItemHolder(item: PostListItem, binding: ItemPostBinding) {
        binding.newItemViewPager.onBindSetup(item)
        binding.postListItem = item
    }

    private fun ViewPager.onBindSetup(item: PostListItem) {
        clearOnPageChangeListeners()
        (adapter as PostImagePagerAdapter).replaceAll(item.newItemList)
        currentItem = item.viewingPosition
        addOnPageChangeListener(object : SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                item.viewingPosition = position
            }
        })
    }

    private class ViewHolderInitializerImpl : ViewHolderInitializer<ItemPostBinding> {
        override fun emptyViewHolderInit(parent: ViewGroup): View =
            DataBindingUtil.inflate<ItemPostEmptyBinding>(
                parent.context.layoutInflater,
                R.layout.item_post_empty,
                parent,
                false
            ).apply {

            }.root


        override fun loadingViewHolderInit(parent: ViewGroup): View =
            DataBindingUtil.inflate<ItemPostLoadingBinding>(
                parent.context.layoutInflater,
                R.layout.item_post_loading,
                parent,
                false
            ).root

        override fun noMoreViewHolderInit(parent: ViewGroup): View =
            DataBindingUtil.inflate<ItemPostNoMoreBinding>(
                parent.context.layoutInflater,
                R.layout.item_post_no_more,
                parent,
                false
            ).root

        override fun itemViewHolderInit(parent: ViewGroup): ItemPostBinding =
            DataBindingUtil.inflate<ItemPostBinding>(
                parent.context.layoutInflater,
                R.layout.item_post,
                parent,
                false
            ).apply {
                newItemViewPager.adapter = PostImagePagerAdapter()
            }
    }
}
