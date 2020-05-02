package com.sabgil.contena.presenter.manage.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sabgil.contena.data.repository.AppRepository
import com.sabgil.contena.data.repository.ContenaRepository
import com.sabgil.contena.domain.model.Shop
import com.sabgil.contena.presenter.base.BaseViewModel
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class ShopManageViewModel @Inject constructor(
    private val appRepository: AppRepository,
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
            ).add()
    }

    fun subscribeShop(shopName: String) {
        appRepository.getFcmToken()?.let { userId ->
            contenaRepository.postSubscription(userId, shopName)
                .compose(apiLoadingSingleTransformer())
                .subscribeBy(
                    onSuccess = {},
                    onError = {
                        _showApiErrorMessage.setValue(it.message ?: "")
                    })
        } ?: _showApiErrorMessage.setValue("아직 User ID가 등록되지 않았습니다.")

    }

    fun unsubscribeShop(shopName: String) {
        appRepository.getFcmToken()?.let { userId ->
            contenaRepository.postUnsubscription(userId, shopName)
                .compose(apiLoadingSingleTransformer())
                .subscribeBy(
                    onSuccess = {},
                    onError = {
                        _showApiErrorMessage.setValue(it.message ?: "")
                    })
        } ?: _showApiErrorMessage.setValue("아직 User ID가 등록되지 않았습니다.")
    }
}