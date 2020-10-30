package com.sabgil.contena.presenter.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sabgil.contena.common.ext.valueOrEmpty
import com.sabgil.contena.data.local.AppSharedPreference
import com.sabgil.contena.data.repository.PostRepository
import com.sabgil.contena.presenter.base.BaseViewModel
import com.sabgil.contena.presenter.home.model.PostItem
import com.sabgil.contena.presenter.home.model.PostItem.*
import com.sabgil.contena.presenter.home.model.TabState
import javax.inject.Inject
import com.sabgil.contena.domain.model.Post as DomainPost

class NewItemTabViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val appSharedPreference: AppSharedPreference
) : BaseViewModel() {

    private val _postList = MutableLiveData<List<PostItem>>(emptyList())
    val postList: LiveData<List<PostItem>> = _postList

    private val _tabState = MutableLiveData(TabState.LOADING_FIRST_PAGE)
    val tabState get() = _tabState

    fun loadFirstPage() {
        val token = appSharedPreference.getToken() ?: return
        postRepository.getPostList(token, FIRST_POST_CURSOR)
            .compose(apiSingleTransformer())
            .doOnSubscribe { _tabState.value = TabState.LOADING_FIRST_PAGE }
            .autoDispose {
                success {
                    _postList.value = arrangePostList(it.first, it.second)
                    _tabState.value = TabState.SUCCESS_FIRST_PAGE
                }
                error {
                    _tabState.value = TabState.FAILED_FIRST_PAGE
                }
            }
    }

    fun loadMorePage(cursor: Long) {
        val token = appSharedPreference.getToken() ?: return
        postRepository.getPostList(token, cursor)
            .compose(apiSingleTransformer())
            .autoDispose {
                success {
                    _postList.value = arrangePostList(it.first, it.second)
                }
                error {
                    // TODO: reload button
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
                    _postList.value = arrangePostList(it.first, it.second)
                    _tabState.value = TabState.SUCCESS_FIRST_PAGE
                }
                error {
                    _tabState.value = TabState.FAILED_FIRST_PAGE
                }
            }
    }

    private fun arrangePostList(cursor: Long, postList: List<DomainPost>): List<PostItem> {
        val presentPostList = postList.map { Post.from(it) }
        val previousPostList = _postList.valueOrEmpty.filter { it !is Loading }

        return if (previousPostList.isEmpty() && presentPostList.isEmpty()) {
            listOf(Empty)
        } else if (presentPostList.isEmpty()) {
            previousPostList + NoMore
        } else {
            previousPostList + presentPostList + Loading(cursor)
        }
    }

    companion object {
        private const val FIRST_POST_CURSOR = -1L
    }
}