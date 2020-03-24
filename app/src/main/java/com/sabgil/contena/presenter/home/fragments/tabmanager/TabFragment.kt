package com.sabgil.contena.presenter.home.fragments.tabmanager

import com.sabgil.contena.presenter.base.InjectFragment

abstract class TabFragment : InjectFragment() {

    abstract var backStackTabIndex: Int?

    abstract fun refreshTab()
}