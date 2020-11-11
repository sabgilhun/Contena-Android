package com.sabgil.contena.presenter.home.activity

import android.content.Context
import android.os.Bundle
import com.sabgil.contena.R
import com.sabgil.contena.common.ext.startOnTop
import com.sabgil.contena.databinding.ActivityHomeBinding
import com.sabgil.contena.presenter.base.BaseActivity
import com.sabgil.contena.presenter.home.model.Tab
import com.sabgil.contena.presenter.home.viewmodel.HomeViewModel

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
            tabSetup(Tab.MAIN, Tab.ADD, Tab.BOOKMARK, Tab.SETTINGS)
            setOnEmpty { finish() }
            setOnChangeTab { tab ->
                Tab.values().find { it == tab }?.let {
                    viewModel.refreshTabIfNeeded(it)
                }
            }
        }
    }

    private fun setupObservers() {
        with(viewModel) {
            changeTab.registerNonNullObserver { binding.bottomNavigation.select(it) }
            currentTabRefresh.registerObserver { binding.bottomNavigation.currentTabRefresh() }
        }
    }

    companion object {
        fun start(context: Context) {
            context.startOnTop(HomeActivity::class)
        }
    }
}
