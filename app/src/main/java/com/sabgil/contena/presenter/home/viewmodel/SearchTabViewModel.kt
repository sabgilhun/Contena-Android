package com.sabgil.contena.presenter.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
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
import java.util.*
import javax.inject.Inject

class SearchTabViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val shopRepository: ShopRepository,
    private val subscriptionRepository: SubscriptionRepository
) : BaseViewModel() {

    private val allShopList = MutableLiveData<List<SearchedShop>>()
    val searchKeyword = MutableLiveData("")
    private val _searchedShop = MediatorLiveData<List<SearchedShop>>()
    val searchedShop: LiveData<List<SearchedShop>> get() = _searchedShop

    init {
        _searchedShop.apply {
            addSource(allShopList) {
                value = filterWithSearchKeyword(it, searchKeyword.value.orEmpty())
            }
            addSource(searchKeyword) {
                value = filterWithSearchKeyword(allShopList.valueOrEmpty, it)
            }
        }
    }

    // TODO : 실패 시 다른 API 진행이 안되므로 예외 처리 필요 (Ex : Refresh 기능)
    fun initialLoadShopData() {
        val userId = appRepository.getFcmToken() ?: return
        shopRepository.getAllShopList(userId)
            .compose(apiLoadingSingleTransformer())
            .subscribeBy(
                onSuccess = { response ->
                    allShopList.value = response.map { SearchedShop.from(it) }
                },
                onError = {

                }
            ).add()
    }

    fun toggleSubscription(searchedShop: SearchedShop) {
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
                    val list = allShopList.valueOrEmpty.mapIndexed { _, shop ->
                        if (shop.shopName == response.updatedShop.shopName) {
                            SearchedShop.from(response)
                        } else {
                            shop
                        }
                    }

                    allShopList.value = list
                },
                onError = {
                    it.printStackTrace()
                }
            )
            .add()
    }

    private fun filterWithSearchKeyword(
        shopList: List<SearchedShop>,
        searchKeyword: String
    ) = shopList.filter {
        val upperCaseName = it.shopName.toUpperCase(Locale.ROOT)
        val upperCaseKeyword = searchKeyword.toUpperCase(Locale.ROOT)
        upperCaseName.contains(upperCaseKeyword)
    }
}