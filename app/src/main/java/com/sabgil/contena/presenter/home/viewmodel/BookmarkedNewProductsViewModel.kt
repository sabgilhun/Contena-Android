package com.sabgil.contena.presenter.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sabgil.contena.data.local.repository.BookmarkRepository
import com.sabgil.contena.presenter.base.BaseViewModel
import com.sabgil.contena.presenter.home.model.BookmarkedNewProductItem
import javax.inject.Inject

class BookmarkedNewProductsViewModel @Inject constructor(
    private val bookmarkRepository: BookmarkRepository,
) : BaseViewModel() {

    private val _bookmarkedNewProducts =
        MutableLiveData<List<BookmarkedNewProductItem>>(emptyList())
    val bookmarkedNewProducts: LiveData<List<BookmarkedNewProductItem>> = _bookmarkedNewProducts

    fun loadBookmarkedNewProducts() {
        bookmarkRepository.findBookmarkedNewProducts()
            .compose(apiLoadingSingleTransformer())
            .autoDispose {
                success {
                    _bookmarkedNewProducts.value =
                        it.map { newProduct -> BookmarkedNewProductItem.from(newProduct) }
                }
                error {
                    handleApiErrorMessage(it)
                }
            }
    }

    fun unregisterBookmark(item: BookmarkedNewProductItem) {
        val newProduct = BookmarkedNewProductItem.toNewProduct(item)

        bookmarkRepository.deleteNewProduct(newProduct)
            .andThen(bookmarkRepository.findBookmarkedNewProducts())
            .compose(apiLoadingSingleTransformer())
            .autoDispose {
                success {
                    _bookmarkedNewProducts.value =
                        it.map { newProduct -> BookmarkedNewProductItem.from(newProduct) }
                }
                error {
                    handleApiErrorMessage(it)
                }
            }
    }
}