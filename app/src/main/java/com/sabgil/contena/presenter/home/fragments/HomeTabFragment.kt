package com.sabgil.contena.presenter.home.fragments

import android.os.Bundle
import android.view.View
import com.sabgil.contena.R
import com.sabgil.contena.databinding.FragmentHomeTabBinding
import com.sabgil.contena.presenter.base.BaseFragment
import com.sabgil.contena.presenter.home.fragments.tabmanager.TabFragment

class HomeTabFragment : BaseFragment<FragmentHomeTabBinding>(R.layout.fragment_home_tab),
    TabFragment {

    override var backStackTabIndex: Int? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun refreshTab() {
    }

    companion object {
        fun newInstance() = HomeTabFragment()
    }
}