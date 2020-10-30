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
        binding.vm = viewModel

        setViews()
        setupObserver()

        viewModel.loadFirstPage()
    }

    override fun refreshTab() {
        // TODO: scroll top
    }

    private fun setViews() {
        with(binding) {
            settingImageButton.setOnClickListener {
                SettingsActivity.start(requireActivity())
            }

            postAdapter = PostAdapter(Handler())
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
    }
}