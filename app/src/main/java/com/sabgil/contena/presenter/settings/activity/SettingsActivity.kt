package com.sabgil.contena.presenter.settings.activity

import android.app.Activity
import android.os.Bundle
import com.sabgil.contena.R
import com.sabgil.contena.common.ext.startWith
import com.sabgil.contena.databinding.ActivitySettingsBinding
import com.sabgil.contena.presenter.base.BaseActivity
import com.sabgil.contena.presenter.manage.activity.ShopManageActivity
import com.sabgil.contena.presenter.settings.viewmodel.SettingsViewModel

class SettingsActivity : BaseActivity<ActivitySettingsBinding>(R.layout.activity_settings) {

    private val viewModel: SettingsViewModel by lazy {
        getViewModel(
            SettingsViewModel::class
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupAppbar()
        setupNavigation()
    }

    private fun setupAppbar() {
        binding.backButton.setOnClickListener { finish() }
    }

    private fun setupNavigation() {
        with(binding) {
            goShopManageButton.setOnClickListener {
                ShopManageActivity.start(this@SettingsActivity)
            }
        }

    }

    companion object {

        fun start(activity: Activity) {
            activity.startWith(SettingsActivity::class)
        }
    }
}