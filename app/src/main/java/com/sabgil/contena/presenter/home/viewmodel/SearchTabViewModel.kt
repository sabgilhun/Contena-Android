package com.sabgil.contena.presenter.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sabgil.contena.data.repository.ContenaRepository
import com.sabgil.contena.domain.model.Shop
import com.sabgil.contena.presenter.base.BaseViewModel
import com.sabgil.contena.presenter.home.enums.SearchingState
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchTabViewModel @Inject constructor(
    private val contenaRepository: ContenaRepository
) : BaseViewModel() {

    private var recommendedShopList: List<Shop> = emptyList()

    private val _searchingState = MutableLiveData<SearchingState>()
    val searchingState: LiveData<SearchingState> = _searchingState

    private val searchStream = PublishSubject.create<String>()

    init {
        loadRecommendedShopList()
        initSearchStream()
    }

    private fun initSearchStream() {
        searchStream
            .doOnNext { _searchingState.value = SearchingState.Searching(it) }
            .debounce(DEBOUNCE_TIMEOUT, TimeUnit.MILLISECONDS)
            .flatMap {
                Observable.zip(
                    Observable.just(it),
                    contenaRepository.getAvailableShopList(it).toObservable().observeOn(Schedulers.io()),
                    BiFunction<String, List<Shop>, SearchResult>(::SearchResult)
                )
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    _searchingState.value =
                        if (it.result.isEmpty()) {
                            SearchingState.Empty(it.keyword)
                        } else {
                            SearchingState.Complete(it.result)
                        }
                },
                onError = {}
            ).add()
    }

    private fun loadRecommendedShopList() {
        if (recommendedShopList.isNotEmpty()) {
            _searchingState.value = SearchingState.NotStarted(recommendedShopList)
            return
        }

        contenaRepository.getAvailableShopList("")
            .compose(apiLoadingSingleTransformer())
            .subscribeBy(
                onSuccess = {
                    recommendedShopList = it
                    _searchingState.value = SearchingState.NotStarted(it)
                },
                onError = {}
            )
            .add()
    }

    fun searchAvailableShopList(searchKeyword: String) {
        if (searchKeyword.isEmpty()) {
            loadRecommendedShopList()
        } else {
            searchStream.onNext(searchKeyword)
        }
    }

    private data class SearchResult(
        val keyword: String,
        val result: List<Shop>
    )

    companion object {
        private const val DEBOUNCE_TIMEOUT = 1000L
    }
}