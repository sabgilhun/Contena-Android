package com.sabgil.contena.presenter.home.adapter.post

import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.sabgil.contena.R
import com.sabgil.contena.common.ext.layoutInflater
import com.sabgil.contena.common.pagemanager.ViewHolderInitializer
import com.sabgil.contena.databinding.ItemPostEmptyBinding
import com.sabgil.contena.databinding.ItemPostLoadingBinding
import com.sabgil.contena.databinding.ItemPostNoMoreBinding


class ViewHolderInitializerImpl : ViewHolderInitializer {
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
}