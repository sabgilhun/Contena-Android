package com.sabgil.contena.presenter.home.viewmodel

import android.util.Log
import com.sabgil.contena.domain.model.Subscription
import com.sabgil.contena.data.repository.ContenaRepository
import com.sabgil.contena.presenter.base.BaseViewModel
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val contenaRepository: ContenaRepository
) : BaseViewModel() {

    fun test() {
        val subscription = Subscription("11", "bluesman")

        contenaRepository.postUnsubscription(subscription)
            .compose(apiLoadingMaybeTransformer())
            .subscribeBy(
                onComplete = {
                    Log.i("테스트", "테스트")
                },
                onError = {
                    _showApiErrorMessage.setValue(it.message ?: "")
                }
            )
            .add()
    }
}