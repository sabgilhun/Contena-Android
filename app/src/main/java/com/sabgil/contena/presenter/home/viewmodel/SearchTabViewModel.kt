package com.sabgil.contena.presenter.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.sabgil.contena.common.SingleLiveEvent
import com.sabgil.contena.common.ext.valueOrEmpty
import com.sabgil.contena.data.remote.dto.PostSubscriptionRequest
import com.sabgil.contena.data.remote.dto.PostUnsubscriptionRequest
import com.sabgil.contena.data.local.repository.AppRepository
import com.sabgil.contena.data.remote.repository.ShopRepository
import com.sabgil.contena.data.remote.repository.SubscriptionRepository
import com.sabgil.contena.presenter.base.BaseViewModel
import com.sabgil.contena.presenter.home.model.BaseSearchedShopItem
import com.sabgil.contena.presenter.home.model.BaseSearchedShopItem.EmptyItem
import com.sabgil.contena.presenter.home.model.BaseSearchedShopItem.ShopItem
import com.sabgil.contena.presenter.home.model.TabState.*
import java.util.*
import javax.inject.Inject

class SearchTabViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val shopRepository: ShopRepository,
    private val subscriptionRepository: SubscriptionRepository
) : BaseViewModel() {

    private val allShopList = MutableLiveData<List<ShopItem>>()
    val searchKeyword = MutableLiveData("")
    private val _searchedShop = MediatorLiveData<List<BaseSearchedShopItem>>()
    val searchedShop: LiveData<List<BaseSearchedShopItem>> get() = _searchedShop
    private val _tabState = MutableLiveData(LOADING_FIRST_PAGE)
    val tabState get() = _tabState
    private val _subscribeSuccess = SingleLiveEvent<Nothing>()
    val subscribeSuccess get() = _subscribeSuccess

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

    fun loadShopData(isFirstLoad: Boolean) {
        val userId = appRepository.getFcmToken() ?: return
        shopRepository.getAllShopList(userId)
            .compose(apiSingleTransformer())
            .doOnSubscribe {
                if (isFirstLoad) {
                    _tabState.value = LOADING_FIRST_PAGE
                } else {
                    _tabState.value = RELOAD_FIRST_PAGE
                }
            }
            .autoDispose {
                success { response ->
                    allShopList.value = response.map { ShopItem.from(it) }
                    _tabState.value = SUCCESS_FIRST_PAGE
                }
                error {
                    _tabState.value = FAILED_FIRST_PAGE
                }
            }
    }

    fun toggleSubscription(searchedShop: ShopItem) {
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
            .autoDispose {
                success { response ->
                    val previousList = allShopList.valueOrEmpty

                    allShopList.value = previousList.map { shop ->
                        if (shop.shopName == response.updatedShop.shopName)
                            ShopItem.from(response)
                        else
                            shop
                    }
                    subscribeSuccess.call()
                }
                error {
                    handleApiErrorMessage(it)
                }
            }
    }

    private fun filterWithSearchKeyword(
        shopList: List<ShopItem>,
        searchKeyword: String
    ): List<BaseSearchedShopItem> {
        val filteredList = shopList.filter {
            val upperCaseName = it.shopName.toUpperCase(Locale.ROOT)
            val upperCaseKeyword = searchKeyword.toUpperCase(Locale.ROOT)
            upperCaseName.contains(upperCaseKeyword)
        }

        return if (filteredList.isEmpty())
            listOf<BaseSearchedShopItem>(EmptyItem)
        else
            filteredList
    }
}