package com.sabgil.contena.presenter.postdetail.viewmodel

import com.sabgil.contena.presenter.base.BaseViewModel
import javax.inject.Inject

class PostDetailViewModel @Inject constructor(
) : BaseViewModel() {

//    private val _newItemList = MutableLiveData<List<DetailNewItem>>()
//    val newItemList: LiveData<List<DetailNewItem>> = _newItemList

    fun loadNewItemList(postId: Long) {
//        contenaRepository.getNewItemList(postId)
//            .compose(apiLoadingSingleTransformer())
//            .subscribeBy(
//                onSuccess = _newItemList::setValue,
//                onError = {}
//            )
//            .add()
    }
}