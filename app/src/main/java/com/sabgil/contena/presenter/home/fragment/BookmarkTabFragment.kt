package com.sabgil.contena.presenter.home.fragment

import android.os.Bundle
import android.view.View
import com.sabgil.contena.R
import com.sabgil.contena.databinding.FragmentBookmarkTabBinding
import com.sabgil.contena.presenter.home.adapter.BookmarkViewPagerAdapter

class BookmarkTabFragment :
    BaseTabFragment<FragmentBookmarkTabBinding>(R.layout.fragment_bookmark_tab) {

    private lateinit var adapter: BookmarkViewPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        with(binding) {
            adapter = BookmarkViewPagerAdapter(parentFragmentManager)
            viewPager.adapter = adapter
            tabLayout.setupWithViewPager(viewPager)
        }
    }

    override fun refreshTab() {
        for (i in 0 until 2) {
            (adapter.getPage(i) as BookmarkPage).refresh()
        }
    }

    override fun scrollOnTop() {
        /* Do Nothing */
    }
}