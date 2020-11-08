package com.sabgil.contena.presenter.home.activity

import android.content.Context
import android.os.Bundle
import com.sabgil.contena.R
import com.sabgil.contena.common.ext.startOnTop
import com.sabgil.contena.databinding.ActivityHomeBinding
import com.sabgil.contena.presenter.base.BaseActivity
import com.sabgil.contena.presenter.home.fragment.BookmarkTabFragment
import com.sabgil.contena.presenter.home.fragment.NewItemTabFragment
import com.sabgil.contena.presenter.home.fragment.SearchTabFragment
import com.sabgil.contena.presenter.home.fragment.SettingsTabFragment
import com.sabgil.contena.presenter.home.viewmodel.HomeViewModel
import com.sabgil.contena.presenter.widget.BottomNavigationBar

class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {

    private val viewModel: HomeViewModel by lazy { getViewModel(HomeViewModel::class) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
        setupObservers()
    }

    override fun onBackPressed() = binding.bottomNavigation.goToBackTab()

    private fun setupViews() {
        with(binding.bottomNavigation) {
            init(R.id.tabContainer, supportFragmentManager)
            tabSetup(
                BottomNavigationBar.Tab(
                    icon = R.drawable.bottom_nav_ic_main,
                    selectedColor = R.color.colorBeigeWhite,
                    unselectedColor = R.color.colorDarkGray,
                    tab = NewItemTabFragment::class.java
                ),

                BottomNavigationBar.Tab(
                    icon = R.drawable.bottom_nav_ic_add,
                    selectedColor = R.color.colorBeigeWhite,
                    unselectedColor = R.color.colorDarkGray,
                    tab = SearchTabFragment::class.java
                ),

                BottomNavigationBar.Tab(
                    icon = R.drawable.bottom_nav_ic_bookmark,
                    selectedColor = R.color.colorBeigeWhite,
                    unselectedColor = R.color.colorDarkGray,
                    tab = BookmarkTabFragment::class.java
                ),

                BottomNavigationBar.Tab(
                    icon = R.drawable.bottom_nav_ic_settings,
                    selectedColor = R.color.colorBeigeWhite,
                    unselectedColor = R.color.colorDarkGray,
                    tab = SettingsTabFragment::class.java
                )
            )

            setOnEmpty { finish() }
            setOnChangeTab {
                if (it is NewItemTabFragment && viewModel.needsPostReload.value == true) {
                    viewModel.needsPostReload.value = false
                    it.loadFirstPage()
                }
            }
        }
    }

    private fun setupObservers() {
        viewModel.changeTab.registerNonNullObserver { binding.bottomNavigation.select(it) }
    }

    companion object {
        fun start(context: Context) {
            context.startOnTop(HomeActivity::class)
        }
    }
}
