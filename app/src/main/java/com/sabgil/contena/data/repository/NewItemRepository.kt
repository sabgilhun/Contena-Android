package com.sabgil.contena.data.repository

import com.sabgil.contena.domain.model.NewProduct
import io.reactivex.Single

interface NewItemRepository {

    fun getNewItemList(postId: Long): Single<List<NewProduct>>
}