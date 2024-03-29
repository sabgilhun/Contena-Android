package com.sabgil.contena.data.remote.api

import com.sabgil.contena.data.remote.dto.GetNewItemListResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewItemApi {

    @GET(value = "new_item")
    fun getNewItemList(
        @Query(value = "post_id") postId: Long
    ): Single<GetNewItemListResponse>
}