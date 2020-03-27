package com.sabgil.contena.presenter.home.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sabgil.contena.R
import com.sabgil.contena.databinding.FragmentHomeTabBinding
import com.sabgil.contena.presenter.base.BaseFragment
import com.sabgil.contena.presenter.home.adapters.ShopShortcutAdapter
import com.sabgil.contena.presenter.home.fragments.tabmanager.Tab
import com.sabgil.contena.presenter.home.viewmodels.HomeTabViewModel
import com.sabgil.contena.presenter.home.widgets.BottomNavigationBar

class HomeTabFragment :
    BaseFragment<FragmentHomeTabBinding>(R.layout.fragment_home_tab), Tab {

    private val viewModel: HomeTabViewModel by lazy {
        getViewModel(HomeTabViewModel::class)
    }

    private lateinit var adapter: ShopShortcutAdapter

    override var backTabIndex: BottomNavigationBar.TabIndex? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupShopShortcutRecyclerView()

        viewModel.loadSubscribedShopList()
        viewModel.subscribedShopList.observe(viewLifecycleOwner, Observer(adapter::addAll))
    }

    private fun setupShopShortcutRecyclerView() {
        adapter = ShopShortcutAdapter()
        binding.shopShortcutRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.shopShortcutRecyclerView.adapter = adapter
    }

    override fun refreshTab() {
    }

    companion object {
        fun newInstance() = HomeTabFragment()
    }
}