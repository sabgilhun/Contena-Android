package com.sabgil.contena.presenter.home.fragment.tabmanager

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.sabgil.contena.presenter.home.fragment.BookmarkTabFragment
import com.sabgil.contena.presenter.home.fragment.HomeTabFragment
import com.sabgil.contena.presenter.home.fragment.SearchTabFragment
import com.sabgil.contena.presenter.home.fragment.SettingsTabFragment
import com.sabgil.contena.presenter.home.widget.BottomNavigationBar

class TabManagerImpl(
    private val fragmentManager: FragmentManager,
    @IdRes private val fragmentContainerId: Int,
    private val emptyBackStackAction: () -> Unit
) : BottomNavigationBar.TabManager {

    private val homeTabFragment: HomeTabFragment by lazy {
        HomeTabFragment.newInstance()
    }

    private val searchTabFragment: SearchTabFragment by lazy {
        SearchTabFragment.newInstance()
    }

    private val bookmarkTabFragment: BookmarkTabFragment by lazy {
        BookmarkTabFragment.newInstance()
    }

    private val settingsTabFragment: SettingsTabFragment by lazy {
        SettingsTabFragment.newInstance()
    }

    override fun initTab(initialTabIndex: BottomNavigationBar.TabIndex) {
        fragmentManager.beginTransaction()
            .addTab(initialTabIndex)
            .commit()
    }

    override fun changeTab(
        oldTabIndex: BottomNavigationBar.TabIndex,
        newTabIndex: BottomNavigationBar.TabIndex
    ) {
        fragmentManager.beginTransaction()
            .addOrShowTab(newTabIndex)
            .hide(oldTabIndex.mapToFragment())
            .commit()
    }

    override fun refreshTab(tabIndex: BottomNavigationBar.TabIndex) {
        tabIndex.mapToTab().refreshTab()
    }

    override fun emptyTabBackStack() {
        emptyBackStackAction()
    }

    override fun setBackTab(
        targetIndex: BottomNavigationBar.TabIndex,
        backIndex: BottomNavigationBar.TabIndex
    ) {
        targetIndex.mapToTab().backTabIndex = backIndex
    }

    override fun emptyBackTab(targetIndex: BottomNavigationBar.TabIndex) {
        targetIndex.mapToTab().backTabIndex = null
    }

    override fun getBackTab(
        targetIndex: BottomNavigationBar.TabIndex
    ): BottomNavigationBar.TabIndex? =
        targetIndex.mapToTab().backTabIndex

    private fun FragmentTransaction.addOrShowTab(
        tabIndex: BottomNavigationBar.TabIndex
    ): FragmentTransaction =
        fragmentManager.find(tabIndex)?.let {
            this.show(it)
        } ?: this.addTab(tabIndex)

    private fun BottomNavigationBar.TabIndex.mapToFragment(): Fragment =
        when (this) {
            BottomNavigationBar.TabIndex.HOME -> homeTabFragment
            BottomNavigationBar.TabIndex.SEARCH -> searchTabFragment
            BottomNavigationBar.TabIndex.BOOKMARK -> bookmarkTabFragment
            BottomNavigationBar.TabIndex.SETTINGS -> settingsTabFragment
        }

    private fun BottomNavigationBar.TabIndex.mapToTab(): Tab =
        when (this) {
            BottomNavigationBar.TabIndex.HOME -> homeTabFragment
            BottomNavigationBar.TabIndex.SEARCH -> searchTabFragment
            BottomNavigationBar.TabIndex.BOOKMARK -> bookmarkTabFragment
            BottomNavigationBar.TabIndex.SETTINGS -> settingsTabFragment
        }

    private fun FragmentTransaction.addTab(
        tabIndex: BottomNavigationBar.TabIndex
    ): FragmentTransaction =
        this.add(fragmentContainerId, tabIndex.mapToFragment(), tabIndex.name)

    private fun FragmentManager.find(tabIndex: BottomNavigationBar.TabIndex): Fragment? =
        this.findFragmentByTag(tabIndex.name)
}