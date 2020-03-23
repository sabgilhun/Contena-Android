package com.sabgil.contena.presenter.home.fragments

import android.os.Bundle
import android.view.View
import com.sabgil.contena.R
import com.sabgil.contena.databinding.FragmentTestBinding
import com.sabgil.contena.presenter.base.BaseFragment
import com.sabgil.contena.presenter.home.viewmodels.HomeViewModel
import com.sabgil.contena.presenter.home.viewmodels.TestViewModel

class TestFragment : BaseFragment<FragmentTestBinding>(R.layout.fragment_test) {

    private val viewModel: TestViewModel by lazy {
        getViewModel(TestViewModel::class)
    }

    private val sharedViewModel: HomeViewModel by lazy {
        getSharedViewModel(HomeViewModel::class)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.test()
        sharedViewModel.test()
    }
}