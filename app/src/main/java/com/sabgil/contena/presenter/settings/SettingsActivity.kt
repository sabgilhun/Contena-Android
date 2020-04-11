package com.sabgil.contena.presenter.settings

import android.app.Activity
import android.os.Bundle
import com.sabgil.contena.R
import com.sabgil.contena.common.ext.startWith
import com.sabgil.contena.databinding.ActivitySettingsBinding
import com.sabgil.contena.presenter.base.BaseActivity

class SettingsActivity : BaseActivity<ActivitySettingsBinding>(R.layout.activity_settings) {

    private val viewModel: SettingsViewModel by lazy { getViewModel(SettingsViewModel::class) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupAppbar()
    }

    private fun setupAppbar() {
        binding.backButton.setOnClickListener { finish() }
    }

    companion object {

        fun start(activity: Activity) {
            activity.startWith(SettingsActivity::class)
        }
    }
}