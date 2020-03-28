package com.sabgil.contena.presenter.home.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.lifecycle.Observer
import com.sabgil.contena.R
import com.sabgil.contena.databinding.FragmentHomeTabBinding
import com.sabgil.contena.presenter.base.BaseFragment
import com.sabgil.contena.presenter.home.adapters.PostAdapter
import com.sabgil.contena.presenter.home.adapters.ShopShortcutAdapter
import com.sabgil.contena.presenter.home.fragments.tabmanager.Tab
import com.sabgil.contena.presenter.home.viewmodels.HomeTabViewModel
import com.sabgil.contena.presenter.home.widgets.BottomNavigationBar

class HomeTabFragment :
    BaseFragment<FragmentHomeTabBinding>(R.layout.fragment_home_tab), Tab {

    private val viewModel: HomeTabViewModel by lazy {
        getViewModel(HomeTabViewModel::class)
    }

    private lateinit var shopShortcutAdapter: ShopShortcutAdapter
    private lateinit var postAdapter: PostAdapter

    override var backTabIndex: BottomNavigationBar.TabIndex? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupShopShortcutRecyclerView()
        setupPostRecyclerView()

        viewModel.loadSubscribedShopList()
        postAdapter.initialDataLoad()

        viewModel.subscribedShopList.observe(
            viewLifecycleOwner,
            Observer(shopShortcutAdapter::addAll)
        )

        viewModel.postList.observe(
            viewLifecycleOwner,
            Observer(postAdapter.dataObserver)
        )
    }

    private fun setupShopShortcutRecyclerView() {
        shopShortcutAdapter = ShopShortcutAdapter()
        binding.shopShortcutRecyclerView.adapter = shopShortcutAdapter
    }

    private fun setupPostRecyclerView() {
        postAdapter = PostAdapter(Handler(Looper.getMainLooper()), viewModel::loadPostList)
        binding.postRecyclerView.adapter = postAdapter
    }

    override fun refreshTab() {
    }

    companion object {
        fun newInstance() = HomeTabFragment()
    }
}