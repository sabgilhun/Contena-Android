package com.sabgil.contena.presenter.splash

import androidx.lifecycle.LiveData
import com.sabgil.contena.common.SingleLiveEvent
import com.sabgil.contena.data.repository.AppRepository
import com.sabgil.contena.presenter.base.BaseViewModel
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val appRepository: AppRepository
) : BaseViewModel() {

    private val _goNext = SingleLiveEvent<Nothing>()
    val goNext: LiveData<Nothing> = _goNext

    private val _getTokenAndGoNext = SingleLiveEvent<Nothing>()
    val getTokenAndGoNext: LiveData<Nothing> = _getTokenAndGoNext

    fun checkFcmToken() {
        if (appRepository.getFcmToken() != null) {
            _goNext.call()
        } else {
            _getTokenAndGoNext.call()
        }
    }

    fun saveToken(token: String) {
        appRepository.putFcmToken(token)
    }
}