package com.sabgil.contena.presenter.home.fragment

import android.os.Bundle
import android.view.View
import com.sabgil.contena.R
import com.sabgil.contena.databinding.FragmentPostBookmarkBinding
import com.sabgil.contena.presenter.base.BaseFragment
import com.sabgil.contena.presenter.home.adapter.BookmarkedPostAdapter
import com.sabgil.contena.presenter.home.model.BookmarkedPostItem
import com.sabgil.contena.presenter.home.model.Tab
import com.sabgil.contena.presenter.home.viewmodel.BookmarkedPostsViewModel
import com.sabgil.contena.presenter.home.viewmodel.HomeViewModel
import com.sabgil.contena.presenter.postdetail.activity.PostDetailActivity

class PostBookmarkFragment :
    BaseFragment<FragmentPostBookmarkBinding>(R.layout.fragment_post_bookmark), BookmarkPage {

    private val viewModel by lazy { getViewModel(BookmarkedPostsViewModel::class) }
    private val homeViewModel: HomeViewModel by lazy { getSharedViewModel(HomeViewModel::class) }

    private lateinit var bookmarkedPostAdapter: BookmarkedPostAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupObserver()

        viewModel.loadBookmarkedPost()
    }

    override fun refresh() {
        binding.bookmarkedPostRecyclerView.scrollToPosition(0)
        viewModel.loadBookmarkedPost()
    }

    private fun setupViews() {
        with(binding) {
            bookmarkedPostAdapter = BookmarkedPostAdapter(requireContext(), Handler())
            bookmarkedPostRecyclerView.adapter = bookmarkedPostAdapter
        }
    }

    private fun setupObserver() {
        viewModel.bookmarkedPosts.registerObserver(bookmarkedPostAdapter::replaceAll)
    }

    inner class Handler {

        fun goToPostDetailActivity(postId: Long, updateDate: String) {
            PostDetailActivity.start(requireActivity(), postId, updateDate)
        }

        fun unregisterBookmark(bookmarkedPostItem: BookmarkedPostItem) {
            viewModel.unregisterBookmark(bookmarkedPostItem)
            homeViewModel.registerNeedsRefresh(Tab.MAIN)
        }
    }
}
