package com.sabgil.contena.presenter.home.fragments

import android.os.Bundle
import android.view.View
import com.sabgil.contena.R
import com.sabgil.contena.databinding.FragmentBookmarkTabBinding
import com.sabgil.contena.presenter.base.BaseFragment
import com.sabgil.contena.presenter.home.fragments.tabmanager.Tab
import com.sabgil.contena.presenter.home.widgets.BottomNavigationBar

class BookmarkTabFragment :
    BaseFragment<FragmentBookmarkTabBinding>(R.layout.fragment_bookmark_tab), Tab {

    override var backTabIndex: BottomNavigationBar.TabIndex? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun refreshTab() {
    }

    companion object {
        fun newInstance() = BookmarkTabFragment()
    }
}