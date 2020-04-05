package com.sabgil.contena.presenter.home.fragment

import android.os.Bundle
import android.view.View
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import com.sabgil.contena.R
import com.sabgil.contena.common.ext.setGone
import com.sabgil.contena.common.ext.setVisible
import com.sabgil.contena.databinding.FragmentSearchTabBinding
import com.sabgil.contena.presenter.base.BaseFragment
import com.sabgil.contena.presenter.home.adapter.SearchedShopAdapter
import com.sabgil.contena.presenter.home.enums.SearchingState
import com.sabgil.contena.presenter.home.fragment.tabmanager.Tab
import com.sabgil.contena.presenter.home.viewmodel.SearchTabViewModel
import com.sabgil.contena.presenter.home.widget.BottomNavigationBar

class SearchTabFragment :
    BaseFragment<FragmentSearchTabBinding>(R.layout.fragment_search_tab), Tab {

    private val viewModel: SearchTabViewModel by lazy {
        getViewModel(SearchTabViewModel::class)
    }

    private lateinit var searchedShopAdapter: SearchedShopAdapter

    override var backTabIndex: BottomNavigationBar.TabIndex? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSearchEditText()
        setupSearchedShopRecyclerView()

        viewModel.setUpSearchTabViewModel()
    }

    private fun SearchTabViewModel.setUpSearchTabViewModel() {
        searchingState.registerObserver {
            when (it) {
                is SearchingState.NotStarted -> {
                    binding.searchingStateTextView.setGone(true)
                    searchedShopAdapter.replaceAll(it.recommendedShopList)
                }
                is SearchingState.Searching -> {
                    binding.searchingStateTextView.setVisible(true)
                    binding.searchingStateTextView.text =
                        HtmlCompat.fromHtml(
                            getString(R.string.fragment_search_tab_searching_text, it.keyword),
                            FROM_HTML_MODE_LEGACY
                        )
                    searchedShopAdapter.replaceAll(emptyList())
                }
                is SearchingState.Empty -> {
                    binding.searchingStateTextView.setVisible(true)
                    binding.searchingStateTextView.text =
                        HtmlCompat.fromHtml(
                            getString(R.string.fragment_search_tab_empty_result_text, it.keyword),
                            FROM_HTML_MODE_LEGACY
                        )
                    searchedShopAdapter.replaceAll(emptyList())
                }
                is SearchingState.Complete -> {
                    binding.searchingStateTextView.setGone(true)
                    searchedShopAdapter.replaceAll(it.searchedShopList)
                }
            }
        }
    }

    private fun setupSearchedShopRecyclerView() {
        searchedShopAdapter = SearchedShopAdapter()
        binding.searchedShopRecyclerView.adapter = searchedShopAdapter
    }

    private fun setupSearchEditText() {
        binding.searchEditText.textChangeListener = { viewModel.searchAvailableShopList(it) }
    }

    override fun refreshTab() {
    }

    companion object {
        fun newInstance() = SearchTabFragment()
    }
}