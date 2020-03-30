package com.sabgil.contena.common.pagemanager

import android.view.View
import android.view.ViewGroup

interface ViewHolderInitializer {

    fun emptyViewHolderInit(parent: ViewGroup): View

    fun loadingViewHolderInit(parent: ViewGroup): View

    fun noMoreViewHolderInit(parent: ViewGroup): View
}