package com.sabgil.contena.presenter.home.fragments.tabmanager

import com.sabgil.contena.presenter.home.widgets.BottomNavigationBar

interface Tab {
    var backTabIndex: BottomNavigationBar.TabIndex?
    fun refreshTab()
}