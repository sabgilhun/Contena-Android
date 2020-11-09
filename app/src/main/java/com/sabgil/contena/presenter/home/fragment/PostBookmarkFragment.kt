package com.sabgil.contena.presenter.home.fragment

import android.os.Bundle
import android.view.View
import com.sabgil.contena.R
import com.sabgil.contena.databinding.FragmentPostBookmarkBinding
import com.sabgil.contena.presenter.base.BaseFragment
import com.sabgil.contena.presenter.home.adapter.BookmarkedPostAdapter
import com.sabgil.contena.presenter.home.model.BookmarkedPostItem
import com.sabgil.contena.presenter.home.viewmodel.BookmarkedPostsViewModel
import com.sabgil.contena.presenter.postdetail.activity.PostDetailActivity

class PostBookmarkFragment :
    BaseFragment<FragmentPostBookmarkBinding>(R.layout.fragment_post_bookmark) {

    private val viewModel by lazy { getViewModel(BookmarkedPostsViewModel::class) }
    private lateinit var bookmarkedPostAdapter: BookmarkedPostAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupObserver()

        viewModel.loadBookmarkedPost()
    }

    private fun setupViews() {
        with(binding) {
            bookmarkedPostAdapter = BookmarkedPostAdapter(
                this@PostBookmarkFragment.requireContext(),
                Handler()
            )
            bookmarkedPostRecyclerView.adapter = bookmarkedPostAdapter
        }
    }

    private fun setupObserver() {
        viewModel.bookmarkedPosts.registerObserver(bookmarkedPostAdapter::replaceAll)
    }

    inner class Handler {
        fun unregisterBookmark(bookmarkedPostItem: BookmarkedPostItem) {
            viewModel.unregisterBookmark(bookmarkedPostItem)
        }

        fun goToPostDetailActivity(postId: Long, updateDate: String) {
            PostDetailActivity.start(requireActivity(), postId, updateDate)
        }
    }
}
