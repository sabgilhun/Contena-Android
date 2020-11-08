package com.sabgil.contena.presenter.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sabgil.contena.common.SingleLiveEvent
import io.reactivex.Completable
import io.reactivex.CompletableTransformer
import io.reactivex.Single
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
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

    protected fun <T> apiSingleTransformer() = SingleTransformer<T, T> {
        it.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    protected fun <T> apiLoadingSingleTransformer() = SingleTransformer<T, T> {
        it.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _isLoading.value = true }
            .doFinally { _isLoading.value = false }
    }

    protected fun <T : Any> Single<T>.autoDispose(block: SingleSubscribeScope<T>.() -> Unit) {
        val scope = SingleSubscribeScope(this, disposables)
        scope.block()
        scope.subscribe()
    }

    protected fun apiCompletableTransformer() = CompletableTransformer {
        it.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    class SingleSubscribeScope<T : Any>(
        private val source: Single<T>,
        private val disposables: CompositeDisposable
    ) {
        private var onSuccess: (T) -> Unit = {}
        var onError: (Throwable) -> Unit = {}

        fun success(onSuccess: (T) -> Unit) {
            this.onSuccess = onSuccess
        }

        fun error(onError: (Throwable) -> Unit) {
            this.onError = onError
        }

        fun subscribe() {
            disposables.add(source.subscribe(onSuccess, onError))
        }
    }

    protected fun apiLoadingCompletableTransformer() = CompletableTransformer {
        it.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _isLoading.value = true }
            .doFinally { _isLoading.value = false }
    }

    protected fun Completable.autoDispose(block: CompletableSubscribeScope.() -> Unit) {
        val scope = CompletableSubscribeScope(this, disposables)
        scope.block()
        scope.subscribe()
    }

    class CompletableSubscribeScope(
        private val source: Completable,
        private val disposables: CompositeDisposable
    ) {
        private var onSuccess: () -> Unit = {}
        var onError: (Throwable) -> Unit = {}

        fun success(onSuccess: () -> Unit) {
            this.onSuccess = onSuccess
        }

        fun error(onError: (Throwable) -> Unit) {
            this.onError = onError
        }

        fun subscribe() {
            disposables.add(source.subscribe(onSuccess, onError))
        }
    }
}
