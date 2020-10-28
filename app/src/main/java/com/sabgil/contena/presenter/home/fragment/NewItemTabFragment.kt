package com.sabgil.contena.presenter.home.fragment

import android.os.Bundle
import android.view.View
import com.sabgil.contena.R
import com.sabgil.contena.databinding.FragmentNewItemTabBinding
import com.sabgil.contena.presenter.home.adapter.PostAdapter
import com.sabgil.contena.presenter.home.fragment.tabmanager.BaseTabFragment
import com.sabgil.contena.presenter.home.viewmodel.NewItemTabViewModel
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
        viewModel.loadPostList()
    }

    private fun setupAppbar() {
        binding.settingImageButton.setOnClickListener {
            SettingsActivity.start(requireActivity())
        }
    }

    private fun setupPostRecyclerView() {
        postAdapter = PostAdapter(Handler())
        binding.postRecyclerView.adapter = postAdapter
    }

    private fun NewItemTabViewModel.setupObserver() {
        postList.registerObserver(postAdapter::replaceAll)
    }

    override fun refreshTab() {
        // TODO: scroll top
    }

    inner class Handler {

        fun loadMorePost(cursor: Long) {
            viewModel.loadPostList(cursor)
        }
    }
}