package com.sabgil.contena.presenter.home.fragments.tabmanager

interface TabFragment {

    var backStackTabIndex: Int?

    fun refreshTab()
}