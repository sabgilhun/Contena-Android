package com.sabgil.contena.presenter.home.fragment

import android.os.Bundle
import android.view.View
import com.sabgil.contena.R
import com.sabgil.contena.databinding.FragmentNewItemTabBinding
import com.sabgil.contena.presenter.home.adapter.PostAdapter
import com.sabgil.contena.presenter.home.fragment.tabmanager.BaseTabFragment
import com.sabgil.contena.presenter.home.viewmodel.HomeViewModel
import com.sabgil.contena.presenter.home.viewmodel.NewItemTabViewModel
import com.sabgil.contena.presenter.postdetail.activity.PostDetailActivity
import com.sabgil.contena.presenter.settings.activity.SettingsActivity

class NewItemTabFragment :
    BaseTabFragment<FragmentNewItemTabBinding>(R.layout.fragment_new_item_tab) {

    private val viewModel: NewItemTabViewModel by lazy {
        getViewModel(NewItemTabViewModel::class)
    }

    private val homeViewModel: HomeViewModel by lazy {
        getSharedViewModel(HomeViewModel::class)
    }

    private lateinit var postAdapter: PostAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel

        setViews()
        setupObserver()

        loadFirstPage()
    }

    override fun refreshTab() {
        // TODO: scroll top
    }

    fun loadFirstPage() {
        viewModel.loadFirstPage()
    }

    private fun setViews() {
        with(binding) {
            settingImageButton.setOnClickListener {
                SettingsActivity.start(requireActivity())
            }

            postAdapter = PostAdapter(this@NewItemTabFragment.requireContext(), Handler())
            postRecyclerView.adapter = postAdapter
        }
    }

    private fun setupObserver() {
        viewModel.postList.registerObserver(postAdapter::replaceAll)
    }

    inner class Handler {

        fun loadMorePost(cursor: Long) {
            viewModel.loadMorePage(cursor)
        }

        fun goToSearchTab() {
            homeViewModel.changeTab.setValue(1)
        }

        fun goToPostDetailActivity(postId: Long, updateDate: String) {
            PostDetailActivity.start(requireActivity(), postId, updateDate)
        }
    }
}