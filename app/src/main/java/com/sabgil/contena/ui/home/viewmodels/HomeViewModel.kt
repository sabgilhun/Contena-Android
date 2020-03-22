package com.sabgil.contena.ui.home.viewmodels

import android.util.Log
import com.sabgil.contena.data.model.Subscription
import com.sabgil.contena.data.repositories.ContenaRepository
import com.sabgil.contena.ui.base.BaseViewModel
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val contenaRepository: ContenaRepository
) : BaseViewModel() {

    fun test() {
        val subscription = Subscription("11", "bluesman")

        contenaRepository.deleteSubscription(subscription)
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