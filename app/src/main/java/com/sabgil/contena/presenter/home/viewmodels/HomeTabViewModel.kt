package com.sabgil.contena.presenter.home.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sabgil.contena.data.repositories.ContenaRepository
import com.sabgil.contena.domain.model.Shop
import com.sabgil.contena.presenter.base.BaseViewModel
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class HomeTabViewModel @Inject constructor(
    private val contenaRepository: ContenaRepository
) : BaseViewModel() {

    private val _subscribedShopList = MutableLiveData<List<Shop>>()
    val subscribedShopList: LiveData<List<Shop>> = _subscribedShopList

    fun loadSubscribedShopList() {
        contenaRepository.getSubscribedShopList("1")
            .compose(apiLoadingSingleTransformer())
            .subscribeBy(
                onSuccess = _subscribedShopList::setValue,
                onError = { _showApiErrorMessage.setValue(it.message ?: "") }
            )
            .add()
    }
}