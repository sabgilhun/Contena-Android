package com.sabgil.contena.presenter.home.fragment

import android.os.Bundle
import android.view.View
import com.sabgil.contena.R
import com.sabgil.contena.databinding.FragmentBookmarkTabBinding
import com.sabgil.contena.presenter.home.adapter.BookmarkViewPagerAdapter
import com.sabgil.contena.presenter.home.viewmodel.HomeViewModel

class BookmarkTabFragment :
    BaseTabFragment<FragmentBookmarkTabBinding>(R.layout.fragment_bookmark_tab) {

    private val homeViewModel: HomeViewModel by lazy {
        getSharedViewModel(HomeViewModel::class)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        with(binding) {
            viewPager.adapter = BookmarkViewPagerAdapter(parentFragmentManager)
            tabLayout.setupWithViewPager(viewPager)
        }
    }

    override fun refreshTab() {
        // TODO: scroll top
    }

    override fun scrollOnTop() {

    }
}