package com.sabgil.contena.presenter.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sabgil.contena.common.ext.valueOrEmpty
import com.sabgil.contena.data.remote.contena.dto.PostSubscriptionRequest
import com.sabgil.contena.data.remote.contena.dto.PostUnsubscriptionRequest
import com.sabgil.contena.data.repository.AppRepository
import com.sabgil.contena.data.repository.ShopRepository
import com.sabgil.contena.data.repository.SubscriptionRepository
import com.sabgil.contena.presenter.base.BaseViewModel
import com.sabgil.contena.presenter.home.model.SearchedShop
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class SearchTabViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val shopRepository: ShopRepository,
    private val subscriptionRepository: SubscriptionRepository
) : BaseViewModel() {

    private val _searchedShop = MutableLiveData<List<SearchedShop>>()
    val searchedShop: LiveData<List<SearchedShop>> get() = _searchedShop

    // TODO : 실패 시 다른 API 진행이 안되므로 예외 처리 필요 (Ex : Refresh 기능)
    fun initialLoadShopData() {
        val userId = appRepository.getFcmToken() ?: return
        shopRepository.getAllShopList(userId)
            .compose(apiLoadingSingleTransformer())
            .subscribeBy(
                onSuccess = { response ->
                    _searchedShop.value = response.map { SearchedShop.from(it) }
                },
                onError = {

                }
            ).add()
    }

    fun toggleSubscription(position: Int, searchedShop: SearchedShop) {
        val userId = appRepository.getFcmToken() ?: return
        val toggleSubscribe = if (searchedShop.isSubscribed) {
            subscriptionRepository.postUnsubscription(
                PostUnsubscriptionRequest(userId, searchedShop.shopName)
            )
        } else {
            subscriptionRepository.postSubscription(
                PostSubscriptionRequest(userId, searchedShop.shopName)
            )
        }

        toggleSubscribe
            .compose(apiLoadingSingleTransformer())
            .subscribeBy(
                onSuccess = { response ->
                    val list = _searchedShop.valueOrEmpty.mapIndexed { index, shop ->
                        if (index == position) {
                            SearchedShop.from(response)
                        } else {
                            shop
                        }
                    }

                    _searchedShop.value = list
                },
                onError = {
                    it.printStackTrace()
                }
            )
            .add()
    }
}