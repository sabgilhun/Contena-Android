package com.sabgil.contena.presenter.postdetail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sabgil.contena.data.repository.ContenaRepository
import com.sabgil.contena.domain.model.DetailNewItem
import com.sabgil.contena.presenter.base.BaseViewModel
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class PostDetailViewModel @Inject constructor(
    private val contenaRepository: ContenaRepository
) : BaseViewModel() {

    private val _newItemList = MutableLiveData<List<DetailNewItem>>()
    val newItemList: LiveData<List<DetailNewItem>> = _newItemList

    fun loadNewItemList(postId: Long) {
        contenaRepository.getNewItemList(postId)
            .compose(apiLoadingSingleTransformer())
            .subscribeBy(
                onSuccess = _newItemList::setValue,
                onError = {}
            )
            .add()
    }
}