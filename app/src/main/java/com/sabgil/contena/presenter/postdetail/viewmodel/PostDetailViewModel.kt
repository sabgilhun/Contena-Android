package com.sabgil.contena.presenter.postdetail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sabgil.contena.data.repository.NewItemRepository
import com.sabgil.contena.presenter.base.BaseViewModel
import com.sabgil.contena.presenter.postdetail.DetailNewProduct
import javax.inject.Inject

class PostDetailViewModel @Inject constructor(
    private val newItemRepository: NewItemRepository
) : BaseViewModel() {

    private val _newItemList = MutableLiveData<List<DetailNewProduct>>()
    val newItemList: LiveData<List<DetailNewProduct>> = _newItemList

    fun loadNewItemList(postId: Long) {
        newItemRepository.getNewItemList(postId)
            .compose(apiLoadingSingleTransformer())
            .autoDispose {
                success { response ->
                    _newItemList.value = response.map { DetailNewProduct.from(it) }
                }
                error {
                    handleApiErrorMessage(it)
                }
            }
    }
}