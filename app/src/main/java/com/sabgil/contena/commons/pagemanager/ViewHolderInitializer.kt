package com.sabgil.contena.commons.pagemanager

import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding

interface ViewHolderInitializer<B : ViewDataBinding> {

    fun emptyViewHolderInit(parent: ViewGroup): View

    fun loadingViewHolderInit(parent: ViewGroup): View

    fun noMoreViewHolderInit(parent: ViewGroup): View

    fun itemViewHolderInit(parent: ViewGroup): B
}