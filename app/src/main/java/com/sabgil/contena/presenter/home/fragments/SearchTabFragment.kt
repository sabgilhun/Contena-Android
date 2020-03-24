package com.sabgil.contena.presenter.home.fragments

import android.os.Bundle
import android.view.View
import com.sabgil.contena.R
import com.sabgil.contena.databinding.FragmentSearchTabBinding
import com.sabgil.contena.presenter.base.BaseFragment
import com.sabgil.contena.presenter.home.fragments.tabmanager.TabFragment

class SearchTabFragment : BaseFragment<FragmentSearchTabBinding>(R.layout.fragment_search_tab),
    TabFragment {

    override var backStackTabIndex: Int? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun refreshTab() {
    }

    companion object {
        fun newInstance() = SearchTabFragment()
    }
}