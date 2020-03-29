package com.sabgil.contena.presenter.home.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sabgil.contena.R
import com.sabgil.contena.databinding.FragmentHomeTabBinding
import com.sabgil.contena.presenter.base.BaseFragment
import com.sabgil.contena.presenter.home.adapter.PostAdapter
import com.sabgil.contena.presenter.home.adapter.ShopShortcutAdapter
import com.sabgil.contena.presenter.home.fragment.tabmanager.Tab
import com.sabgil.contena.presenter.home.viewmodel.HomeTabViewModel
import com.sabgil.contena.presenter.home.widget.BottomNavigationBar

class HomeTabFragment :
    BaseFragment<FragmentHomeTabBinding>(R.layout.fragment_home_tab), Tab {

    private val viewModel: HomeTabViewModel by lazy {
        getViewModel(HomeTabViewModel::class)
    }

    private val postRecyclerViewScrollHandler =
        object : RecyclerView.OnScrollListener() {
            var oldPosition: Int = -1

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val position = (recyclerView.layoutManager as LinearLayoutManager)
                    .findFirstVisibleItemPosition()

                if (position != oldPosition) {
                    val post = (recyclerView.adapter as PostAdapter).getItem(position)
                    binding.uploadDateHintTextView.text = post?.uploadDate
                    oldPosition = position
                }
            }
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
        binding.postRecyclerView.addOnScrollListener(postRecyclerViewScrollHandler)
    }

    override fun refreshTab() {
    }

    companion object {
        fun newInstance() = HomeTabFragment()
    }
}