package com.sabgil.contena.presenter.home.fragment

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.sabgil.contena.R
import com.sabgil.contena.databinding.FragmentNewItemTabBinding
import com.sabgil.contena.presenter.home.adapter.PostAdapter
import com.sabgil.contena.presenter.home.fragment.tabmanager.BaseTabFragment
import com.sabgil.contena.presenter.home.viewmodel.NewItemTabViewModel
import com.sabgil.contena.presenter.postdetail.activity.PostDetailActivity
import com.sabgil.contena.presenter.settings.activity.SettingsActivity

class NewItemTabFragment :
    BaseTabFragment<FragmentNewItemTabBinding>(R.layout.fragment_new_item_tab) {

    private val viewModel: NewItemTabViewModel by lazy {
        getViewModel(NewItemTabViewModel::class)
    }

    private lateinit var postAdapter: PostAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAppbar()
        setupPostRecyclerView()

        viewModel.setupObserver()

        viewModel.loadSubscribedShopList()
        postAdapter.initialDataLoad()
    }

    private fun setupAppbar() {
        binding.settingImageButton.setOnClickListener {
            SettingsActivity.start(requireActivity())
        }
    }

    private fun setupPostRecyclerView() {
        postAdapter = PostAdapter(
            NavigatorImpl(requireActivity()),
            Handler(Looper.getMainLooper()),
            viewModel::loadPostList
        )
        binding.postRecyclerView.adapter = postAdapter
    }

    private fun NewItemTabViewModel.setupObserver() {
        postList.registerObserver(postAdapter.dataObserver)
    }

    override fun refreshTab() {
    }

    companion object {
        fun newInstance() = NewItemTabFragment()
    }

    private class NavigatorImpl(private val activity: Activity) : PostAdapter.Navigator {
        override fun goToTotalProduction(postId: Long, shopName: String, uploadDate: String) {
            PostDetailActivity.start(activity, postId, shopName, uploadDate)
        }

        override fun goToOriginWeb() {
        }
    }
}