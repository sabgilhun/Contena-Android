package com.sabgil.contena.presenter.home.viewmodel

import androidx.lifecycle.LiveData
import com.sabgil.contena.common.SingleLiveEvent
import com.sabgil.contena.presenter.base.BaseViewModel
import com.sabgil.contena.presenter.home.model.Tab
import javax.inject.Inject

class HomeViewModel @Inject constructor() : BaseViewModel() {

    private val needsRefreshTabs = mutableSetOf<Tab>()

    private val _changeTab = SingleLiveEvent<Tab>()
    val changeTab: LiveData<Tab> = _changeTab

    private val _currentTabRefresh = SingleLiveEvent<Nothing>()
    val currentTabRefresh: LiveData<Nothing> = _currentTabRefresh

    fun registerNeedsRefresh(tab: Tab) {
        needsRefreshTabs.add(tab)
    }

    fun changeTab(tab: Tab) {
        _changeTab.setValue(tab)
        refreshTabIfNeeded(tab)
    }

    fun refreshTabIfNeeded(changingTab: Tab) {
        if (needsRefreshTabs.remove(changingTab)) {
            _currentTabRefresh.call()
        }
    }
}
