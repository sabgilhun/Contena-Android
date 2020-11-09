package com.sabgil.contena.presenter.home.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.sabgil.contena.R
import com.sabgil.contena.databinding.FragmentNewProductBookmarkBinding
import com.sabgil.contena.presenter.base.BaseFragment
import com.sabgil.contena.presenter.home.adapter.BookmarkedNewProductAdapter
import com.sabgil.contena.presenter.home.model.BookmarkedNewProductItem
import com.sabgil.contena.presenter.home.viewmodel.BookmarkedNewProductsViewModel
import com.sabgil.contena.presenter.web.WebViewActivity

class NewProductBookmarkFragment :
    BaseFragment<FragmentNewProductBookmarkBinding>(R.layout.fragment_new_product_bookmark) {

    private val viewModel by lazy { getViewModel(BookmarkedNewProductsViewModel::class) }
    private lateinit var bookmarkNewProductAdapter: BookmarkedNewProductAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupObserver()

        viewModel.loadBookmarkedNewProducts()
    }

    private fun setupViews() {
        with(binding) {
            bookmarkNewProductAdapter = BookmarkedNewProductAdapter(
                this@NewProductBookmarkFragment.requireContext(),
                Handler()
            )
            bookmarkedNewProductRecyclerView.layoutManager = GridLayoutManager(
                this@NewProductBookmarkFragment.requireContext(),
                2
            )
            bookmarkedNewProductRecyclerView.adapter = bookmarkNewProductAdapter
        }
    }

    private fun setupObserver() {
        viewModel.bookmarkedNewProducts.registerObserver(bookmarkNewProductAdapter::replaceAll)
    }

    inner class Handler {

        fun showPageOnWebView(pageUrl: String) =
            WebViewActivity.start(requireActivity(), pageUrl)

        fun unregisterBookmark(bookmarkedNewProductItem: BookmarkedNewProductItem) {
            viewModel.unregisterBookmark(bookmarkedNewProductItem)
        }
    }
}

