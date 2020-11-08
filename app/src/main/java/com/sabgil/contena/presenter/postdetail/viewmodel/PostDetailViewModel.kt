package com.sabgil.contena.presenter.postdetail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sabgil.contena.common.ext.valueOrEmpty
import com.sabgil.contena.data.local.repository.BookmarkRepository
import com.sabgil.contena.data.remote.repository.NewItemRepository
import com.sabgil.contena.domain.model.NewProduct
import com.sabgil.contena.presenter.base.BaseViewModel
import com.sabgil.contena.presenter.postdetail.DetailNewProduct
import javax.inject.Inject

class PostDetailViewModel @Inject constructor(
    private val newItemRepository: NewItemRepository,
    private val bookmarkRepository: BookmarkRepository,
) : BaseViewModel() {

    private val _newItemList = MutableLiveData<List<DetailNewProduct>>()
    val newItemList: LiveData<List<DetailNewProduct>> = _newItemList

    fun loadNewItemList(postId: Long) {
        newItemRepository.getNewItemList(postId)
            .flatMap {
                bookmarkRepository.findBookmarkedNewProducts()
                    .map { bookmarkedProducts -> it to bookmarkedProducts }
            }
            .compose(apiLoadingSingleTransformer())
            .autoDispose {
                success { (newProducts: List<NewProduct>, bookmarkedProducts: List<NewProduct>) ->
                    val bookmarkedIds = bookmarkedProducts.map { it.productId }.toSet()
                    _newItemList.value =
                        newProducts.map { DetailNewProduct.from(it, bookmarkedIds) }
                }
                error {
                    handleApiErrorMessage(it)
                }
            }
    }

    fun registerBookmarkNewProduct(detailNewProduct: DetailNewProduct) {
        val newProduct = DetailNewProduct.toNewProduct(detailNewProduct)
        val toggleBookmark = if (detailNewProduct.isBookmarked) {
            bookmarkRepository.deleteNewProduct(newProduct)
        } else {
            bookmarkRepository.insertNewProduct(newProduct)
        }

        toggleBookmark
            .compose(apiLoadingCompletableTransformer())
            .autoDispose {
                success {
                    val index = _newItemList.valueOrEmpty.indexOf(detailNewProduct)
                    if (index != -1) {
                        val previous = _newItemList.valueOrEmpty.toMutableList()
                        previous[index] =
                            detailNewProduct.copy(isBookmarked = !detailNewProduct.isBookmarked)
                        _newItemList.value = previous
                    }
                }
                error {
                    handleApiErrorMessage(it)
                }
            }
    }
}