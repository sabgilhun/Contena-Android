package com.sabgil.contena.data.remote.repository

import com.sabgil.contena.data.mapper.NewProductMapper
import com.sabgil.contena.data.remote.api.NewItemApi
import com.sabgil.contena.domain.model.NewProduct
import io.reactivex.Single
import javax.inject.Inject

class NewItemRepositoryImpl @Inject constructor(
    private val newItemApi: NewItemApi,
    private val newProductMapper: NewProductMapper
) : NewItemRepository {

    override fun getNewItemList(postId: Long): Single<List<NewProduct>> =
        newItemApi.getNewItemList(postId).map(newProductMapper::toNewProducts)
}