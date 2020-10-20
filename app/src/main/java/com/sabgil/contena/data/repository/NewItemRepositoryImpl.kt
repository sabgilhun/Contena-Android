package com.sabgil.contena.data.repository

import com.sabgil.contena.data.remote.contena.api.NewItemApi
import com.sabgil.contena.domain.model.NewItem
import io.reactivex.Single
import javax.inject.Inject

class NewItemRepositoryImpl @Inject constructor(
    private val newItemApi: NewItemApi
) : NewItemRepository {

    override fun getNewItemList(postId: Long): Single<List<NewItem>> {
        TODO("Not yet implemented")
    }
}