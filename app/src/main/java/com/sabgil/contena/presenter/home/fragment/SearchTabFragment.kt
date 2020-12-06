package com.sabgil.contena.presenter.home.fragment

import android.os.Bundle
import android.view.View
import com.sabgil.contena.R
import com.sabgil.contena.databinding.FragmentSearchTabBinding
import com.sabgil.contena.presenter.home.adapter.SearchedShopAdapter
import com.sabgil.contena.presenter.home.model.BaseSearchedShopItem
import com.sabgil.contena.presenter.home.model.Tab
import com.sabgil.contena.presenter.home.viewmodel.HomeViewModel
import com.sabgil.contena.presenter.home.viewmodel.SearchTabViewModel

class SearchTabFragment : BaseTabFragment<FragmentSearchTabBinding>(R.layout.fragment_search_tab) {

    private val viewModel: SearchTabViewModel by lazy {
        getViewModel(SearchTabViewModel::class)
    }

    private val homeViewModel: HomeViewModel by lazy {
        getSharedViewModel(HomeViewModel::class)
    }

    private lateinit var searchedShopAdapter: SearchedShopAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel

        setViews()
        setupObserver()

        viewModel.loadShopData(true)
    }

    override fun refreshTab() {
        /* Do Nothing */
    }

    override fun scrollOnTop() {
        binding.searchedShopRecyclerView.scrollToPosition(0)
    }

    private fun setViews() {
        with(binding) {
            searchEditText.textChangeListener = {
                viewModel.searchKeyword.value = it
            }

            searchedShopAdapter =
                SearchedShopAdapter(this@SearchTabFragment.requireContext(), Handler())
            searchedShopRecyclerView.adapter = searchedShopAdapter
        }
    }

    private fun setupObserver() {
        with(viewModel) {
            searchedShop.registerObserver(searchedShopAdapter::update)
            subscribeSuccess.registerObserver { homeViewModel.registerNeedsRefresh(Tab.MAIN) }
        }

    }

    inner class Handler {

        fun toggleSubscription(searchedShop: BaseSearchedShopItem.ShopItem) {
            viewModel.toggleSubscription(searchedShop)
        }
    }
}