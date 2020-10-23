package com.sabgil.contena.presenter.home.fragment

import android.os.Bundle
import android.view.View
import com.sabgil.contena.R
import com.sabgil.contena.databinding.FragmentSearchTabBinding
import com.sabgil.contena.presenter.home.adapter.SearchedShopAdapter
import com.sabgil.contena.presenter.home.fragment.tabmanager.BaseTabFragment
import com.sabgil.contena.presenter.home.viewmodel.SearchTabViewModel

class SearchTabFragment : BaseTabFragment<FragmentSearchTabBinding>(R.layout.fragment_search_tab) {

    private val viewModel: SearchTabViewModel by lazy {
        getViewModel(SearchTabViewModel::class)
    }

    override fun refreshTab() {
        // TODO: scroll top
    }

    private lateinit var searchedShopAdapter: SearchedShopAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSearchEditText()
        setupSearchedShopRecyclerView()

        viewModel.setupObserver()
        viewModel.initialLoadShopData()
    }

    private fun setupSearchedShopRecyclerView() {
        searchedShopAdapter = SearchedShopAdapter { doSubscribe, shopName ->
            if (doSubscribe) {
                viewModel.subscribeShop(shopName)
            } else {
                viewModel.unsubscribeShop(shopName)
            }
        }

        binding.searchedShopRecyclerView.adapter = searchedShopAdapter
    }

    private fun setupSearchEditText() {
        binding.searchEditText.textChangeListener = { viewModel.searchAvailableShopList(it) }
    }

    private fun SearchTabViewModel.setupObserver() {
        searchedShop.registerObserver {
            searchedShopAdapter.replaceAll(it)
        }
    }

    companion object {
        fun newInstance() = SearchTabFragment()
    }
}