package com.sabgil.contena.presenter.home.activities

import android.os.Bundle
import com.sabgil.contena.R
import com.sabgil.contena.databinding.ActivityHomeBinding
import com.sabgil.contena.presenter.base.BaseActivity
import com.sabgil.contena.presenter.home.fragments.TestFragment
import com.sabgil.contena.presenter.home.viewmodels.HomeViewModel

class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {

    private val viewModel: HomeViewModel by lazy {
        getViewModel(HomeViewModel::class)
    }

    lateinit var fragment: TestFragment

    var flag: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
