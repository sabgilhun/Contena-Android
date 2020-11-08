package com.sabgil.contena.presenter.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sabgil.contena.common.ext.valueOrEmpty
import com.sabgil.contena.data.local.pref.AppSharedPreference
import com.sabgil.contena.data.local.repository.BookmarkRepository
import com.sabgil.contena.data.remote.repository.PostRepository
import com.sabgil.contena.domain.model.Post
import com.sabgil.contena.presenter.base.BaseViewModel
import com.sabgil.contena.presenter.home.model.BasePostItem
import com.sabgil.contena.presenter.home.model.BasePostItem.*
import com.sabgil.contena.presenter.home.model.TabState
import javax.inject.Inject

class NewItemTabViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val bookmarkRepository: BookmarkRepository,
    private val appSharedPreference: AppSharedPreference
) : BaseViewModel() {

    private val _postList = MutableLiveData<List<BasePostItem>>(emptyList())
    val postList: LiveData<List<BasePostItem>> = _postList

    private val _tabState = MutableLiveData(TabState.LOADING_FIRST_PAGE)
    val tabState get() = _tabState

    fun loadFirstPage() {
        val token = appSharedPreference.getToken() ?: return
        postRepository.getPostList(token, FIRST_POST_CURSOR)
            .flatMap {
                bookmarkRepository.findBookmarkedPosts()
                    .map { bookmarkedPosts -> Triple(it.first, it.second, bookmarkedPosts) }
            }
            .compose(apiSingleTransformer())
            .doOnSubscribe { _tabState.value = TabState.LOADING_FIRST_PAGE }
            .autoDispose {
                success { (cursor: Long, posts: List<Post>, bookmarkedPosts: List<Post>) ->
                    _postList.value = arrangeFirstPosts(cursor, posts, bookmarkedPosts)
                    _tabState.value = TabState.SUCCESS_FIRST_PAGE
                }
                error {
                    _tabState.value = TabState.FAILED_FIRST_PAGE
                }
            }
    }

    fun loadMorePage(cursor: Long) {
        loadMorePage(cursor, false)
    }

    fun reloadFailedPage(cursor: Long) {
        loadMorePage(cursor, true)
    }

    private fun loadMorePage(cursor: Long, useLoadingBar: Boolean) {
        val token = appSharedPreference.getToken() ?: return
        postRepository.getPostList(token, cursor)
            .flatMap {
                bookmarkRepository.findBookmarkedPosts()
                    .map { bookmarkedPosts -> Triple(it.first, it.second, bookmarkedPosts) }
            }
            .compose(if (useLoadingBar) apiLoadingSingleTransformer() else apiSingleTransformer())
            .autoDispose {
                success { (cursor: Long, posts: List<Post>, bookmarkedPosts: List<Post>) ->
                    _postList.value = appendMorePosts(cursor, posts, bookmarkedPosts)
                }
                error {
                    _postList.value = filterNotPostItem() + MoreLoadFailItem(cursor)
                }
            }
    }

    fun reloadFirstPage() {
        val token = appSharedPreference.getToken() ?: return
        postRepository.getPostList(token, FIRST_POST_CURSOR)
            .flatMap {
                bookmarkRepository.findBookmarkedPosts()
                    .map { bookmarkedPosts -> Triple(it.first, it.second, bookmarkedPosts) }
            }
            .compose(apiSingleTransformer())
            .doOnSubscribe { _tabState.value = TabState.RELOAD_FIRST_PAGE }
            .autoDispose {
                success { (cursor: Long, posts: List<Post>, bookmarkedPosts: List<Post>) ->
                    _postList.value = arrangeFirstPosts(cursor, posts, bookmarkedPosts)
                    _tabState.value = TabState.SUCCESS_FIRST_PAGE
                }
                error {
                    _tabState.value = TabState.FAILED_FIRST_PAGE
                }
            }
    }

    fun registerBookmarkPost(postItem: PostItem) {
        val post = PostItem.toPost(postItem)
        val toggleBookmark = if (postItem.isBookmarked) {
            bookmarkRepository.deletePost(post)
        } else {
            bookmarkRepository.insertPost(post)
        }

        toggleBookmark
            .compose(apiLoadingCompletableTransformer())
            .autoDispose {
                success {
                    val index = _postList.valueOrEmpty.indexOf(postItem)
                    if (index != -1) {
                        val previous = _postList.valueOrEmpty.toMutableList()
                        previous[index] = postItem.copy(isBookmarked = !postItem.isBookmarked)
                        _postList.value = previous
                    }
                }
                error {
                    handleApiErrorMessage(it)
                }
            }
    }

    private fun arrangeFirstPosts(
        cursor: Long,
        posts: List<Post>,
        bookmarkedPosts: List<Post>
    ): List<BasePostItem> {
        val bookmarkedIds = bookmarkedPosts.map { it.postId }.toSet()
        val presentPostList = posts.map { PostItem.from(it, bookmarkedIds) }

        return if (presentPostList.isEmpty()) {
            listOf(EmptyItem)
        } else {
            presentPostList + LoadingItem(cursor)
        }
    }

    private fun appendMorePosts(
        cursor: Long,
        posts: List<Post>,
        bookmarkedPosts: List<Post>
    ): List<BasePostItem> {
        val bookmarkedIds = bookmarkedPosts.map { it.postId }.toSet()
        val presentPostList = posts.map { PostItem.from(it, bookmarkedIds) }
        val previousPostList = filterNotPostItem()

        return if (previousPostList.isEmpty() && presentPostList.isEmpty()) {
            listOf(EmptyItem)
        } else if (presentPostList.isEmpty()) {
            previousPostList + NoMoreItem
        } else {
            previousPostList + presentPostList + LoadingItem(cursor)
        }
    }

    private fun filterNotPostItem() =
        _postList.valueOrEmpty.filter { it !is LoadingItem && it !is MoreLoadFailItem }

    companion object {
        private const val FIRST_POST_CURSOR = -1L
    }
}