package com.sabgil.contena.presenter.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sabgil.contena.common.pagemanager.PageHolder
import com.sabgil.contena.data.local.AppSharedPreference
import com.sabgil.contena.data.repository.PostRepository
import com.sabgil.contena.presenter.base.BaseViewModel
import com.sabgil.contena.presenter.home.model.PostListItem
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class NewItemTabViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val appSharedPreference: AppSharedPreference
) : BaseViewModel() {

    private val _postList = MutableLiveData<PageHolder<PostListItem>>()
    val postList: LiveData<PageHolder<PostListItem>> = _postList

    fun loadPostList(cursor: Long) {
        val token = appSharedPreference.getToken() ?: return
        postRepository.getPostList(token, if (cursor == 0L) -1 else cursor)
            .compose(apiLoadingSingleTransformer())
            .subscribeBy(
                onSuccess = {
                    _postList.value = PageHolder(it.second.map { post ->
                        PostListItem(
                            post.postId,
                            "",
                            post.uploadDate,
                            post.shopName,
                            post.shopLogoUrl,
                            post.subscriberCount.toString(),
                            post.newItemList
                        )
                    }, it.first)
                },
                onError = { _showApiErrorMessage.setValue(it.message ?: "") }
            ).add()
    }
}