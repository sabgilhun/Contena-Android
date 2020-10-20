package com.sabgil.contena.presenter.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sabgil.contena.domain.model.Shop
import com.sabgil.contena.presenter.base.BaseViewModel
import com.sabgil.contena.presenter.home.enums.SearchingState
import com.sabgil.contena.presenter.home.model.SearchedShop
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class SearchTabViewModel @Inject constructor(
//    private val appRepository: AppRepository,
//    private val contenaRepository: ContenaRepository
) : BaseViewModel() {

    private var recommendedShopList: List<SearchedShop> = emptyList()

    private var subscribedShops: List<Shop> = emptyList()

    private val _searchingState = MutableLiveData<SearchingState>()
    val searchingState: LiveData<SearchingState> = _searchingState

    private val searchStream = PublishSubject.create<String>()

    init {
        initSearchStream()
    }

    // TODO : 에러 발생시에도 Stream 끊키지 않도록 수정 or Refresh 기능 필요
    private fun initSearchStream() {
//        searchStream
//            .doOnNext { _searchingState.value = SearchingState.Searching(it) }
//            .debounce(DEBOUNCE_TIMEOUT, TimeUnit.MILLISECONDS)
//            .flatMap {
//                Observable.zip(
//                    Observable.just(it),
//                    contenaRepository.getAvailableShopList(it).toObservable().observeOn(Schedulers.io()),
//                    BiFunction<String, List<Shop>, SearchResult>(::SearchResult)
//                )
//            }
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeBy(
//                onNext = {
//                    _searchingState.value =
//                        if (it.resultShops.isEmpty()) {
//                            SearchingState.Empty(it.keyword)
//                        } else {
//                            SearchingState.Complete(
//                                searchedShops = it.resultShops.map { shop ->
//                                    SearchedShop.from(shop, subscribedShops)
//                                })
//                        }
//                },
//                onError = {
//                    it.printStackTrace()
//                }
//            ).add()
    }

    // TODO : 실패 시 다른 API 진행이 안되므로 예외 처리 필요 (Ex : Refresh 기능)
    fun initialLoadShopData() {
//        val userId = appRepository.getFcmToken()
//
//        if (userId == null) {
//            _showApiErrorMessage.setValue("아직 User ID가 등록되지 않았습니다.")
//            return
//        }
//
//        contenaRepository.getSubscribedShopList(userId)
//            .flatMap {
//                subscribedShops = it
//                contenaRepository.getAvailableShopList("")
//            }
//            .compose(apiLoadingSingleTransformer())
//            .subscribeBy(
//                onSuccess = {
//                    recommendedShopList =
//                        it.map { shop -> SearchedShop.from(shop, subscribedShops) }
//                    _searchingState.value = SearchingState.NotStarted(recommendedShopList)
//                },
//                onError = { _showApiErrorMessage.setValue(it.message.orEmpty()) }
//            ).add()
    }

    fun searchAvailableShopList(searchKeyword: String) {
        if (searchKeyword.isEmpty()) {
            _searchingState.value = SearchingState.NotStarted(recommendedShopList)
        } else {
            searchStream.onNext(searchKeyword)
        }
    }

    fun subscribeShop(shopName: String) {
//        val userId = appRepository.getFcmToken()
//        val searchingState = _searchingState.value
//
//        if (userId == null) {
//            _showApiErrorMessage.setValue("아직 User ID가 등록되지 않았습니다.")
//            return
//        }
//
//        if (searchingState !is SearchingState.Complete) return
//
//        contenaRepository.postSubscription(userId, shopName)
//            .compose(apiLoadingSingleTransformer())
//            .subscribeBy(
//                onSuccess = {
//                    searchingState.searchedShops.map {
//                        if (it.shopName == shopName) it.apply {
//                            subscribed = true
//                        } else it
//                    }
//                },
//                onError = {
//                    _showApiErrorMessage.setValue(it.message.orEmpty())
//                }).add()
    }

    fun unsubscribeShop(shopName: String) {
//        val userId = appRepository.getFcmToken()
//        val searchingState = _searchingState.value
//
//        if (userId == null) {
//            _showApiErrorMessage.setValue("아직 User ID가 등록되지 않았습니다.")
//            return
//        }
//
//        if (searchingState !is SearchingState.Complete) return
//
//        contenaRepository.postUnsubscription(userId, shopName)
//            .compose(apiLoadingSingleTransformer())
//            .subscribeBy(
//                onSuccess = {
//                    searchingState.searchedShops.map {
//                        if (it.shopName == shopName) it.apply {
//                            subscribed = false
//                        } else it
//                    }
//                },
//                onError = {
//                    _showApiErrorMessage.setValue(it.message.orEmpty())
//                }).add()
    }

    private data class SearchResult(
        val keyword: String,
        val resultShops: List<Shop>
    )

    companion object {
        private const val DEBOUNCE_TIMEOUT = 1000L
    }
}