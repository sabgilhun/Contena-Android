package com.sabgil.contena.presenter.home.fragments

import android.os.Bundle
import android.view.View
import com.sabgil.contena.R
import com.sabgil.contena.databinding.FragmentBookmarkTabBinding
import com.sabgil.contena.presenter.base.BaseFragment
import com.sabgil.contena.presenter.home.fragments.tabmanager.TabFragment

class BookmarkTabFragment : BaseFragment<FragmentBookmarkTabBinding>(R.layout.fragment_bookmark_tab),
    TabFragment {

    override var backStackTabIndex: Int? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun refreshTab() {
    }

    companion object {
        fun newInstance() = BookmarkTabFragment()
    }
}