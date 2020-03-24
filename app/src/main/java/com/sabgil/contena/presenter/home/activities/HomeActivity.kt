package com.sabgil.contena.presenter.home.activities

import android.os.Bundle
import android.util.Log
import com.sabgil.contena.R
import com.sabgil.contena.databinding.ActivityHomeBinding
import com.sabgil.contena.presenter.base.BaseActivity
import com.sabgil.contena.presenter.home.fragments.BookmarkTabFragment
import com.sabgil.contena.presenter.home.fragments.HomeTabFragment
import com.sabgil.contena.presenter.home.fragments.SearchTabFragment
import com.sabgil.contena.presenter.home.fragments.SettingsTabFragment
import com.sabgil.contena.presenter.home.fragments.tabmanager.TabFragment
import com.sabgil.contena.presenter.home.fragments.tabmanager.TabFragmentManager
import com.sabgil.contena.presenter.home.widgets.BottomNavigationBar

class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {

    private lateinit var tabFragmentManager: TabFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.bottomNavigation.tabClickConsumer = {
            Log.i(it.toString(), it.toString())
        }

        setUpTabFragmentManager()
        setUpBottomNavigationBar()
    }

    override fun onBackPressed() {
        if (tabFragmentManager.goToBackTab()) {
            super.onBackPressed()
        }
    }

    private fun setUpTabFragmentManager() {
        tabFragmentManager = TabFragmentManager(
            supportFragmentManager,
            R.id.tabContainer,
            this::newInstanceTabFragments
        )
    }

    private fun setUpBottomNavigationBar() {
        binding.bottomNavigation.tabClickConsumer = tabFragmentManager::selectTab
        binding.bottomNavigation.selectedTab = BottomNavigationBar.Tab.HOME
    }

    private fun newInstanceTabFragments(index: Int): TabFragment =
        when (BottomNavigationBar.Tab.from(index)) {
            BottomNavigationBar.Tab.HOME -> HomeTabFragment.newInstance()
            BottomNavigationBar.Tab.SEARCH -> SearchTabFragment.newInstance()
            BottomNavigationBar.Tab.BOOKMARK -> BookmarkTabFragment.newInstance()
            BottomNavigationBar.Tab.SETTINGS -> SettingsTabFragment.newInstance()
        }
}
