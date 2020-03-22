package com.sabgil.contena.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sabgil.contena.commons.SingleLiveEvent
import io.reactivex.MaybeTransformer
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


abstract class BaseViewModel : ViewModel() {

    protected val disposables = CompositeDisposable()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    protected val _showApiErrorMessage = SingleLiveEvent<String>()
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

    protected fun <T> apiLoadingSingleTransformer(): SingleTransformer<T, T> {
        return SingleTransformer {
            it.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _isLoading.value = true }
                .doFinally { _isLoading.value = false }
        }
    }

    protected fun <T> apiLoadingMaybeTransformer(): MaybeTransformer<T, T> {
        return MaybeTransformer {
            it.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _isLoading.value = true }
                .doFinally { _isLoading.value = false }
        }
    }
}
