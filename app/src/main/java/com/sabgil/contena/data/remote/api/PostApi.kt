package com.sabgil.contena.data.remote.api

import com.sabgil.contena.data.remote.dto.GetPostListResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PostApi {

    @GET(value = "post")
    fun getPostList(
        @Query(value = "user_id") userId: String,
        @Query(value = "cursor") cursor: Long?
    ): Single<GetPostListResponse>
}