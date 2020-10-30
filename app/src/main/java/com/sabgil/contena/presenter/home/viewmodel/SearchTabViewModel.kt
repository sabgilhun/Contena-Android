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
import com.sabgil.contena.presenter.home.model.SearchedShopItem
import com.sabgil.contena.presenter.home.model.SearchedShopItem.Empty
import com.sabgil.contena.presenter.home.model.SearchedShopItem.Shop
import com.sabgil.contena.presenter.home.model.TabState.*
import java.util.*
import javax.inject.Inject

class SearchTabViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val shopRepository: ShopRepository,
    private val subscriptionRepository: SubscriptionRepository
) : BaseViewModel() {

    private val allShopList = MutableLiveData<List<Shop>>()
    val searchKeyword = MutableLiveData("")
    private val _searchedShop = MediatorLiveData<List<SearchedShopItem>>()
    val searchedShop: LiveData<List<SearchedShopItem>> get() = _searchedShop
    private val _tabState = MutableLiveData(LOADING_FIRST_PAGE)
    val tabState get() = _tabState

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
                    allShopList.value = response.map { Shop.from(it) }
                    _tabState.value = SUCCESS_FIRST_PAGE
                }
                error {
                    _tabState.value = FAILED_FIRST_PAGE
                }
            }
    }

    fun toggleSubscription(searchedShop: Shop) {
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
                            Shop.from(response)
                        else
                            shop
                    }
                }
                error {
                    handleApiErrorMessage(it)
                }
            }
    }

    private fun filterWithSearchKeyword(
        shopList: List<Shop>,
        searchKeyword: String
    ): List<SearchedShopItem> {
        val filteredList = shopList.filter {
            val upperCaseName = it.shopName.toUpperCase(Locale.ROOT)
            val upperCaseKeyword = searchKeyword.toUpperCase(Locale.ROOT)
            upperCaseName.contains(upperCaseKeyword)
        }

        return if (filteredList.isEmpty())
            listOf<SearchedShopItem>(Empty)
        else
            filteredList
    }
}