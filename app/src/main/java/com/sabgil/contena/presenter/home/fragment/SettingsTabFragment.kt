package com.sabgil.contena.presenter.home.fragment

import android.os.Bundle
import android.view.View
import com.sabgil.contena.R
import com.sabgil.contena.databinding.FragmentSettingsTabBinding
import com.sabgil.contena.presenter.home.fragment.tabmanager.BaseTabFragment
import com.sabgil.contena.presenter.home.viewmodel.HomeViewModel

class SettingsTabFragment :
    BaseTabFragment<FragmentSettingsTabBinding>(R.layout.fragment_settings_tab) {

    private val homeViewModel: HomeViewModel by lazy {
        getSharedViewModel(HomeViewModel::class)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun refreshTab() {
        // TODO: scroll top
    }
}