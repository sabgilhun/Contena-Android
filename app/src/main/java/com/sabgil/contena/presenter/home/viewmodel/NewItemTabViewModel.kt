package com.sabgil.contena.presenter.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sabgil.contena.common.adapter.BaseItem
import com.sabgil.contena.data.local.AppSharedPreference
import com.sabgil.contena.data.repository.PostRepository
import com.sabgil.contena.presenter.base.BaseViewModel
import com.sabgil.contena.presenter.home.model.EmptyItem
import com.sabgil.contena.presenter.home.model.LoadingItem
import com.sabgil.contena.presenter.home.model.NoMoreItem
import com.sabgil.contena.presenter.home.model.PostItem
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class NewItemTabViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val appSharedPreference: AppSharedPreference
) : BaseViewModel() {

    private val _postList = MutableLiveData<List<BaseItem>>(emptyList())
    val postList: LiveData<List<BaseItem>> = _postList

    fun loadPostList(cursor: Long = 0) {
        val token = appSharedPreference.getToken() ?: return
        postRepository.getPostList(token, if (cursor == 0L) -1 else cursor)
            .compose(apiLoadingSingleTransformer())
            .subscribeBy(
                onSuccess = { response ->
                    val presentPostList = response.second.map(PostItem.Companion::from)
                    val previousPostList = getPreviousPostList().filter { it !is LoadingItem }

                    if (previousPostList.isEmpty() && presentPostList.isEmpty()) {
                        _postList.value = listOf(EmptyItem)
                    } else if (presentPostList.isEmpty()) {
                        _postList.value = previousPostList + NoMoreItem
                    } else {
                        _postList.value =
                            previousPostList + presentPostList + LoadingItem(response.first)
                    }
                },
                onError = { _showApiErrorMessage.setValue(it.message ?: "") }
            ).add()
    }

    private fun getPreviousPostList() = _postList.value?.toMutableList() ?: mutableListOf()
}