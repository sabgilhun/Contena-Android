package com.sabgil.contena.data.remote.repository

import com.sabgil.contena.domain.model.NewProduct
import io.reactivex.Single

interface NewItemRepository {

    fun getNewItemList(postId: Long): Single<List<NewProduct>>
}