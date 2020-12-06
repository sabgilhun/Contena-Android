package com.sabgil.contena.presenter.home.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.sabgil.contena.R
import com.sabgil.contena.databinding.FragmentNewProductBookmarkBinding
import com.sabgil.contena.presenter.base.BaseFragment
import com.sabgil.contena.presenter.home.adapter.BookmarkedNewProductAdapter
import com.sabgil.contena.presenter.home.model.BookmarkedNewProductItem
import com.sabgil.contena.presenter.home.model.Tab
import com.sabgil.contena.presenter.home.viewmodel.BookmarkedNewProductsViewModel
import com.sabgil.contena.presenter.home.viewmodel.HomeViewModel
import com.sabgil.contena.presenter.web.WebViewActivity

class NewProductBookmarkFragment :
    BaseFragment<FragmentNewProductBookmarkBinding>(R.layout.fragment_new_product_bookmark),
    BookmarkPage {

    private val viewModel by lazy { getViewModel(BookmarkedNewProductsViewModel::class) }
    private val homeViewModel: HomeViewModel by lazy { getSharedViewModel(HomeViewModel::class) }

    private lateinit var bookmarkNewProductAdapter: BookmarkedNewProductAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupObserver()

        viewModel.loadBookmarkedNewProducts()
    }

    override fun refresh() {
        viewModel.loadBookmarkedNewProducts()
        binding.bookmarkedNewProductRecyclerView.scrollToPosition(0)
    }

    private fun setupViews() {
        with(binding) {
            bookmarkNewProductAdapter = BookmarkedNewProductAdapter(requireContext(), Handler())
            bookmarkedNewProductRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
            bookmarkedNewProductRecyclerView.adapter = bookmarkNewProductAdapter
        }
    }

    private fun setupObserver() {
        viewModel.bookmarkedNewProducts.registerObserver(bookmarkNewProductAdapter::update)
    }

    inner class Handler {

        fun showPageOnWebView(pageUrl: String) =
            WebViewActivity.start(requireActivity(), pageUrl)

        fun unregisterBookmark(bookmarkedNewProductItem: BookmarkedNewProductItem) {
            viewModel.unregisterBookmark(bookmarkedNewProductItem)
            homeViewModel.registerNeedsRefresh(Tab.MAIN)
        }
    }
}

