package com.sabgil.contena.presenter.home.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.sabgil.contena.R
import com.sabgil.contena.common.ext.startOnTop
import com.sabgil.contena.databinding.ActivityHomeBinding
import com.sabgil.contena.presenter.base.BaseActivity
import com.sabgil.contena.presenter.home.fragment.tabmanager.TabManagerImpl
import com.sabgil.contena.presenter.widget.BottomNavigationBar

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

    companion object {

        fun start(context: Context) {
            context.startOnTop(HomeActivity::class)
        }
    }
}
