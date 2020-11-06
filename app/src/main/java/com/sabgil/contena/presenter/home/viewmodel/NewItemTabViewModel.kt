package com.sabgil.contena.presenter.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sabgil.contena.common.ext.valueOrEmpty
import com.sabgil.contena.data.local.AppSharedPreference
import com.sabgil.contena.data.repository.PostRepository
import com.sabgil.contena.presenter.base.BaseViewModel
import com.sabgil.contena.presenter.home.model.BasePostItem
import com.sabgil.contena.presenter.home.model.BasePostItem.*
import com.sabgil.contena.presenter.home.model.TabState
import javax.inject.Inject
import com.sabgil.contena.domain.model.Post as DomainPost

class NewItemTabViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val appSharedPreference: AppSharedPreference
) : BaseViewModel() {

    private val _postList = MutableLiveData<List<BasePostItem>>(emptyList())
    val postList: LiveData<List<BasePostItem>> = _postList

    private val _tabState = MutableLiveData(TabState.LOADING_FIRST_PAGE)
    val tabState get() = _tabState

    fun loadFirstPage() {
        val token = appSharedPreference.getToken() ?: return
        postRepository.getPostList(token, FIRST_POST_CURSOR)
            .compose(apiSingleTransformer())
            .doOnSubscribe { _tabState.value = TabState.LOADING_FIRST_PAGE }
            .autoDispose {
                success {
                    _postList.value = arrangeFirstPosts(it.first, it.second)
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
            .compose(if (useLoadingBar) apiLoadingSingleTransformer() else apiSingleTransformer())
            .autoDispose {
                success {
                    _postList.value = appendMorePosts(it.first, it.second)
                }
                error {
                    _postList.value = filterNotPostItem() + MoreLoadFailItem(cursor)
                }
            }
    }

    fun reloadFirstPage() {
        val token = appSharedPreference.getToken() ?: return
        postRepository.getPostList(token, FIRST_POST_CURSOR)
            .compose(apiSingleTransformer())
            .doOnSubscribe { _tabState.value = TabState.RELOAD_FIRST_PAGE }
            .autoDispose {
                success {
                    _postList.value = arrangeFirstPosts(it.first, it.second)
                    _tabState.value = TabState.SUCCESS_FIRST_PAGE
                }
                error {
                    _tabState.value = TabState.FAILED_FIRST_PAGE
                }
            }
    }

    private fun arrangeFirstPosts(cursor: Long, postList: List<DomainPost>): List<BasePostItem> {
        val presentPostList = postList.map { PostItem.from(it) }

        return if (presentPostList.isEmpty()) {
            listOf(EmptyItem)
        } else {
            presentPostList + LoadingItem(cursor)
        }
    }

    private fun appendMorePosts(cursor: Long, postList: List<DomainPost>): List<BasePostItem> {
        val presentPostList = postList.map { PostItem.from(it) }
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