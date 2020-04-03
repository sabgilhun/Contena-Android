package com.sabgil.contena.presenter.home.fragment

import android.os.Bundle
import android.view.View
import com.sabgil.contena.R
import com.sabgil.contena.databinding.FragmentSearchTabBinding
import com.sabgil.contena.presenter.base.BaseFragment
import com.sabgil.contena.presenter.home.fragment.tabmanager.Tab
import com.sabgil.contena.presenter.home.viewmodel.SearchTabViewModel
import com.sabgil.contena.presenter.home.widget.BottomNavigationBar

class SearchTabFragment :
    BaseFragment<FragmentSearchTabBinding>(R.layout.fragment_search_tab), Tab {

    private val viewModel: SearchTabViewModel by lazy {
        getViewModel(SearchTabViewModel::class)
    }

    override var backTabIndex: BottomNavigationBar.TabIndex? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadRecommendedShopList()

        binding.searchEditText.textChangeListener = { viewModel.searchAvailableShopList(it) }
    }

    override fun refreshTab() {
    }

    companion object {
        fun newInstance() = SearchTabFragment()
    }
}