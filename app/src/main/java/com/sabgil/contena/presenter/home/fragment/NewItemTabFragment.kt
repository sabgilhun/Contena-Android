package com.sabgil.contena.presenter.home.fragment

import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import com.sabgil.contena.R
import com.sabgil.contena.databinding.FragmentNewItemTabBinding
import com.sabgil.contena.presenter.home.adapter.PostAdapter
import com.sabgil.contena.presenter.home.model.BasePostItem
import com.sabgil.contena.presenter.home.model.Tab
import com.sabgil.contena.presenter.home.viewmodel.HomeViewModel
import com.sabgil.contena.presenter.home.viewmodel.NewItemTabViewModel
import com.sabgil.contena.presenter.postdetail.activity.PostDetailActivity

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

        viewModel.loadFirstPage()
    }

    override fun refreshTab() {
        binding.postRecyclerView.scrollToPosition(0)
        viewModel.loadFirstPage()
    }

    override fun scrollOnTop() {
        binding.postRecyclerView.scrollToPosition(0)
    }

    private fun setViews() {
        with(binding) {
            postAdapter = PostAdapter(requireContext(), Handler())
            postRecyclerView.adapter = postAdapter
        }
    }

    private fun setupObserver() {
        viewModel.postList.registerObserver(postAdapter::update)
    }

    inner class Handler {

        fun loadMorePost(cursor: Long) {
            viewModel.loadMorePage(cursor)
        }

        fun reloadFailedPage(cursor: Long) {
            viewModel.reloadFailedPage(cursor)
        }

        fun goToSearchTab() {
            homeViewModel.changeTab(Tab.ADD)
        }

        fun goToPostDetailActivity(postId: Long, updateDate: String) {
            PostDetailActivity.start(requireActivity(), postId, updateDate)
        }

        fun showPostMenu(anchor: View) {
            PopupMenu(context, anchor).apply {
                setOnMenuItemClickListener { item ->
                    return@setOnMenuItemClickListener when (item.itemId) {
                        R.id.report -> true // TODO: 다이얼르그 추가
                        else -> false
                    }
                }
                inflate(R.menu.menu_post_item)
                show()
            }
        }

        fun registerBookmarkPost(postItem: BasePostItem.PostItem) {
            viewModel.registerBookmarkPost(postItem)
            homeViewModel.registerNeedsRefresh(Tab.BOOKMARK)
        }
    }
}