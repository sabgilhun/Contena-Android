package com.sabgil.contena.data.repository

import com.sabgil.contena.data.mapper.NewItemMapper
import com.sabgil.contena.data.remote.contena.api.NewItemApi
import com.sabgil.contena.domain.model.NewProduct
import io.reactivex.Single
import javax.inject.Inject

class NewItemRepositoryImpl @Inject constructor(
    private val newItemApi: NewItemApi,
    private val newItemMapper: NewItemMapper
) : NewItemRepository {

    override fun getNewItemList(postId: Long): Single<List<NewProduct>> =
        newItemApi.getNewItemList(postId).map(newItemMapper::toNewItemList)
}