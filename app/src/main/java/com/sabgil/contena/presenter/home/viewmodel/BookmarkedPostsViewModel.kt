package com.sabgil.contena.presenter.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sabgil.contena.data.local.repository.BookmarkRepository
import com.sabgil.contena.presenter.base.BaseViewModel
import com.sabgil.contena.presenter.home.model.BookmarkedPostItem
import javax.inject.Inject

class BookmarkedPostsViewModel @Inject constructor(
    private val bookmarkRepository: BookmarkRepository,
) : BaseViewModel() {

    private val _bookmarkedPosts = MutableLiveData<List<BookmarkedPostItem>>(emptyList())
    val bookmarkedPosts: LiveData<List<BookmarkedPostItem>> = _bookmarkedPosts

    fun loadBookmarkedPost() {
        bookmarkRepository.findBookmarkedPosts()
            .compose(apiLoadingSingleTransformer())
            .autoDispose {
                success {
                    _bookmarkedPosts.value = it.map { post -> BookmarkedPostItem.from(post) }
                }
                error {
                    handleApiErrorMessage(it)
                }
            }
    }

    fun unregisterBookmark(bookmarkedPostItem: BookmarkedPostItem) {
        val post = BookmarkedPostItem.toPost(bookmarkedPostItem)

        bookmarkRepository.deletePost(post)
            .andThen(bookmarkRepository.findBookmarkedPosts())
            .compose(apiLoadingSingleTransformer())
            .autoDispose {
                success {
                    _bookmarkedPosts.value = it.map { post -> BookmarkedPostItem.from(post) }
                }
                error {
                    handleApiErrorMessage(it)
                }
            }
    }
}