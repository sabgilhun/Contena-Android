package com.sabgil.contena.presenter.home.fragment.tabmanager

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.sabgil.contena.presenter.base.BaseFragment

abstract class BaseTabFragment<B : ViewDataBinding>(
    @LayoutRes layoutId: Int
) : BaseFragment<B>(layoutId) {

    abstract fun refreshTab()
}