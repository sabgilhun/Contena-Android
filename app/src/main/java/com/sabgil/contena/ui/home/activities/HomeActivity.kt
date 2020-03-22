package com.sabgil.contena.ui.home.activities

import android.os.Bundle
import com.sabgil.contena.R
import com.sabgil.contena.databinding.ActivityHomeBinding
import com.sabgil.contena.ext.setVisible
import com.sabgil.contena.ui.base.BaseActivity
import com.sabgil.contena.ui.home.viewmodels.HomeViewModel
import kotlinx.android.synthetic.main.widget_progress_bar.view.*

class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {

    private val viewModel: HomeViewModel by lazy {
        getViewModel(HomeViewModel::class)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.test.setOnClickListener {
            viewModel.test()
        }
    }
}
