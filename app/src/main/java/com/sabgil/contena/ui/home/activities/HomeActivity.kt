package com.sabgil.contena.ui.home.activities

import android.os.Bundle
import com.sabgil.contena.R
import com.sabgil.contena.databinding.ActivityHomeBinding
import com.sabgil.contena.ui.base.BaseActivity
import com.sabgil.contena.ui.home.viewmodels.HomeViewModel

class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {

    val viewModel: HomeViewModel by lazy {
        getViewModel(HomeViewModel::class)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
