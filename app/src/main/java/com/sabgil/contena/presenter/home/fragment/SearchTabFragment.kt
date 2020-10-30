package com.sabgil.contena.presenter.home.fragment

import android.os.Bundle
import android.view.View
import com.sabgil.contena.R
import com.sabgil.contena.databinding.FragmentSearchTabBinding
import com.sabgil.contena.presenter.home.adapter.SearchedShopAdapter
import com.sabgil.contena.presenter.home.fragment.tabmanager.BaseTabFragment
import com.sabgil.contena.presenter.home.model.BaseSearchedShopItem
import com.sabgil.contena.presenter.home.viewmodel.SearchTabViewModel

class SearchTabFragment : BaseTabFragment<FragmentSearchTabBinding>(R.layout.fragment_search_tab) {

    private val viewModel: SearchTabViewModel by lazy {
        getViewModel(SearchTabViewModel::class)
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
        // TODO: scroll top
    }


    private fun setViews() {
        with(binding) {
            searchEditText.textChangeListener = {
                viewModel.searchKeyword.value = it
            }

            searchedShopAdapter = SearchedShopAdapter(Handler())
            searchedShopRecyclerView.adapter = searchedShopAdapter
        }
    }

    private fun setupObserver() {
        viewModel.searchedShop.registerObserver(searchedShopAdapter::replaceAll)
    }

    inner class Handler {

        fun toggleSubscription(searchedShop: BaseSearchedShopItem.ShopItem) {
            viewModel.toggleSubscription(searchedShop)
        }
    }
}