package com.sabgil.contena.presenter.home.fragment

import android.os.Bundle
import android.view.View
import com.sabgil.contena.R
import com.sabgil.contena.databinding.FragmentSettingsTabBinding

class SettingsTabFragment :
    BaseTabFragment<FragmentSettingsTabBinding>(R.layout.fragment_settings_tab) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun refreshTab() {
        /* Do Nothing */
    }

    override fun scrollOnTop() {
        /* Do Nothing */
    }
}