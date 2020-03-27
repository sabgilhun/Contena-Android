package com.sabgil.contena.presenter.home.activities

import android.os.Bundle
import com.sabgil.contena.R
import com.sabgil.contena.databinding.ActivityHomeBinding
import com.sabgil.contena.presenter.base.BaseActivity
import com.sabgil.contena.presenter.home.fragments.tabmanager.TabManagerImpl
import com.sabgil.contena.presenter.home.widgets.BottomNavigationBar

class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.bottomNavigation.setup()
    }

    override fun onBackPressed() = binding.bottomNavigation.goToBackTab()

    private fun BottomNavigationBar.setup() {
        this.tabManager = TabManagerImpl(
            fragmentManager = supportFragmentManager,
            fragmentContainerId = R.id.tabContainer,
            emptyBackStackAction = this@HomeActivity::finish
        )
    }
}
