package com.sabgil.contena.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sabgil.contena.commons.SingleLiveEvent
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


abstract class BaseViewModel : ViewModel() {

    protected val disposables = CompositeDisposable()

    private val _blockingLoading = MutableLiveData<Boolean>()
    val blockingLoading: LiveData<Boolean> = _blockingLoading

    private val _nonBlockingLoading = MutableLiveData<Boolean>()
    val nonBlockingLoading: LiveData<Boolean> = _nonBlockingLoading

    private val _showApiErrorMessage = SingleLiveEvent<String>()
    val showApiErrorMessage: LiveData<String> = _showApiErrorMessage

    override fun onCleared() {
        disposables.clear()
    }

    protected fun handleApiErrorMessage(throwable: Throwable) {
        _showApiErrorMessage.setValue(throwable.message ?: "")
    }

    protected fun Disposable.add() {
        disposables.add(this)
    }

    protected fun <T> apiBlockingLoadingTransformer(): SingleTransformer<T, T> {
        return SingleTransformer {
            it.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _blockingLoading.value = true }
                .doFinally { _blockingLoading.value = false }
        }
    }

    protected fun <T> apiNonBlockingLoadingTransformer(): SingleTransformer<T, T> {
        return SingleTransformer {
            it.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _nonBlockingLoading.value = true }
                .doFinally { _nonBlockingLoading.value = false }
        }
    }
}
