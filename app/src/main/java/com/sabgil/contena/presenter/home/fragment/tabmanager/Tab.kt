package com.sabgil.contena.presenter.home.fragment.tabmanager

import com.sabgil.contena.presenter.widget.BottomNavigationBar

interface Tab {
    var backTabIndex: BottomNavigationBar.TabIndex?
    fun refreshTab()
}