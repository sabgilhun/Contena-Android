package com.sabgil.contena.presenter.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sabgil.contena.common.pagemanager.PageHolder
import com.sabgil.contena.data.repository.ContenaRepository
import com.sabgil.contena.domain.model.Shop
import com.sabgil.contena.presenter.base.BaseViewModel
import com.sabgil.contena.presenter.home.model.PostListItem
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class HomeTabViewModel @Inject constructor(
    private val contenaRepository: ContenaRepository
) : BaseViewModel() {

    private val _subscribedShopList = MutableLiveData<List<Shop>>()
    val subscribedShopList: LiveData<List<Shop>> = _subscribedShopList

    private val _postList = MutableLiveData<PageHolder<PostListItem>>()
    val postList: LiveData<PageHolder<PostListItem>> = _postList

    fun loadSubscribedShopList() {
        contenaRepository.getSubscribedShopList("1")
            .compose(apiLoadingSingleTransformer())
            .subscribeBy(
                onSuccess = _subscribedShopList::setValue,
                onError = { _showApiErrorMessage.setValue(it.message ?: "") }
            ).add()
    }

    fun loadPostList(cursor: Long) {
        contenaRepository.getPostList("1", if (cursor == 0L) -1 else cursor)
            .compose(apiLoadingSingleTransformer())
            .map { PageHolder(it.first.map(PostListItem.Companion::from), it.second) }
            .subscribeBy(
                onSuccess = _postList::setValue,
                onError = { _showApiErrorMessage.setValue(it.message ?: "") }
            ).add()
    }
}