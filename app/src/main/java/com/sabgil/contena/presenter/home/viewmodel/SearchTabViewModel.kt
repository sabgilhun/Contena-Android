package com.sabgil.contena.presenter.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sabgil.contena.data.repository.ContenaRepository
import com.sabgil.contena.domain.model.Shop
import com.sabgil.contena.presenter.base.BaseViewModel
import com.sabgil.contena.presenter.home.enums.SearchingState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchTabViewModel @Inject constructor(
    private val contenaRepository: ContenaRepository
) : BaseViewModel() {

    private val _subscribedShopList = MutableLiveData<List<Shop>>()
    val subscribedShopList: LiveData<List<Shop>> = _subscribedShopList

    private val _searchingState = MutableLiveData<SearchingState>()
    val searchingState: LiveData<SearchingState> = _searchingState

    private val searchStream = PublishSubject.create<String>()

    init {
        searchStream
            .doOnNext { _searchingState.value = SearchingState.SEARCHING }
            .debounce(DEBOUNCE_TIMEOUT, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.computation())
            .observeOn(Schedulers.io())
            .flatMap { return@flatMap contenaRepository.getAvailableShopList(it).toObservable() }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { _searchingState.value = SearchingState.COMPLETE }
            .subscribeBy(
                onNext = _subscribedShopList::setValue,
                onError = {}
            ).add()
    }

    fun loadRecommendedShopList() {
        contenaRepository.getAvailableShopList("")
            .compose(apiLoadingSingleTransformer())
            .doOnSuccess { _searchingState.value = SearchingState.NOT_STARTED }
            .subscribeBy(
                onSuccess = _subscribedShopList::setValue,
                onError = {}
            )
            .add()
    }

    fun searchAvailableShopList(searchKeyword: String) {
        if (searchKeyword.isEmpty()) {
            _searchingState.value = SearchingState.COMPLETE
            _subscribedShopList.value = emptyList()
        } else {
            searchStream.onNext(searchKeyword)
        }
    }

    companion object {
        const val DEBOUNCE_TIMEOUT = 1000L
    }
}