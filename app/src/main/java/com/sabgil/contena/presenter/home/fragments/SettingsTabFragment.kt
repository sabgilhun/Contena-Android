package com.sabgil.contena.presenter.home.fragments

import android.os.Bundle
import android.view.View
import com.sabgil.contena.R
import com.sabgil.contena.databinding.FragmentSettingsTabBinding
import com.sabgil.contena.presenter.home.fragments.tabmanager.BaseTabFragment

class SettingsTabFragment :
    BaseTabFragment<FragmentSettingsTabBinding>(R.layout.fragment_settings_tab) {

    override var backStackTabIndex: Int? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun refreshTab() {
    }

    companion object {
        fun newInstance() = SettingsTabFragment()
    }
}