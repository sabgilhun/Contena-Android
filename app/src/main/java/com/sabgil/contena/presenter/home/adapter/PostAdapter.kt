package com.sabgil.contena.presenter.home.adapter

import android.os.Handler
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.sabgil.contena.R
import com.sabgil.contena.common.ext.layoutInflater
import com.sabgil.contena.common.pagemanager.PageManageAdapter
import com.sabgil.contena.common.pagemanager.ViewHolderInitializer
import com.sabgil.contena.databinding.ItemPostBinding
import com.sabgil.contena.databinding.ItemPostEmptyBinding
import com.sabgil.contena.databinding.ItemPostLoadingBinding
import com.sabgil.contena.databinding.ItemPostNoMoreBinding
import com.sabgil.contena.domain.model.Post

class PostAdapter(
    handler: Handler,
    loadMoreData: (Long) -> Unit
) : PageManageAdapter<Post, ItemPostBinding>(
    handler,
    loadMoreData,
    ViewHolderInitializerImpl()
) {
    override fun onBindItemHolder(item: Post, binding: ItemPostBinding) {
        binding.post = item
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
            ).apply {

            }.root

        override fun noMoreViewHolderInit(parent: ViewGroup): View =
            DataBindingUtil.inflate<ItemPostNoMoreBinding>(
                parent.context.layoutInflater,
                R.layout.item_post_no_more,
                parent,
                false
            ).apply {

            }.root

        override fun itemViewHolderInit(parent: ViewGroup): ItemPostBinding =
            DataBindingUtil.inflate<ItemPostBinding>(
                parent.context.layoutInflater,
                R.layout.item_post,
                parent,
                false
            ).apply {

            }
    }
}
