package com.sabgil.contena.presenter.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sabgil.contena.data.local.AppSharedPreference
import com.sabgil.contena.data.repository.PostRepository
import com.sabgil.contena.presenter.base.BaseViewModel
import com.sabgil.contena.presenter.home.model.PostItem
import com.sabgil.contena.presenter.home.model.PostItem.*
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class NewItemTabViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val appSharedPreference: AppSharedPreference
) : BaseViewModel() {

    private val _postList = MutableLiveData<List<PostItem>>(emptyList())
    val postList: LiveData<List<PostItem>> = _postList

    fun loadPostList(cursor: Long = 0) {
        val token = appSharedPreference.getToken() ?: return
        postRepository.getPostList(token, if (cursor == 0L) -1 else cursor)
            .compose(apiLoadingSingleTransformer())
            .subscribeBy(
                onSuccess = { response ->
                    val presentPostList = response.second.map(Post.Companion::from)
                    val previousPostList = getPreviousPostList().filter { it !is Loading }

                    if (previousPostList.isEmpty() && presentPostList.isEmpty()) {
                        _postList.value = listOf(Empty)
                    } else if (presentPostList.isEmpty()) {
                        _postList.value = previousPostList + NoMore
                    } else {
                        _postList.value =
                            previousPostList + presentPostList + Loading(response.first)
                    }
                },
                onError = { _showApiErrorMessage.setValue(it.message ?: "") }
            ).add()
    }

    private fun getPreviousPostList() = _postList.value?.toMutableList() ?: mutableListOf()
}