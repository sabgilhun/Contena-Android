package com.sabgil.contena.data.remote.contena

import com.sabgil.contena.data.remote.contena.request.PostUnsubscriptionRequest
import com.sabgil.contena.data.remote.contena.request.PostSubscriptionRequest
import com.sabgil.contena.data.remote.contena.response.GetNewItemListResponse
import com.sabgil.contena.data.remote.contena.response.GetPostListResponse
import com.sabgil.contena.data.remote.contena.response.GetShopListResponse
import io.reactivex.Maybe
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ContenaApi {

    @GET(value = "post")
    fun getPostList(
        @Query(value = "user_id") userId: String,
        @Query(value = "cursor") cursor: Long?
    ): Single<GetPostListResponse>

    @GET(value = "new_item")
    fun getNewItemList(
        @Query(value = "post_id") postId: Long
    ): Single<GetNewItemListResponse>

    @GET(value = "shop_list/available")
    fun getAvailableShopList(
        @Query(value = "search_keyword") searchKeyword: String
    ): Single<GetShopListResponse>

    @GET(value = "shop_list/subscription")
    fun getSubscribedShopList(
        @Query(value = "user_id") userId: String
    ): Single<GetShopListResponse>

    @POST(value = "subscription")
    fun postSubscription(
        @Body postSubscriptionRequest: PostSubscriptionRequest
    ): Maybe<Void>

    @POST(value = "unsubscription")
    fun postUnsubscription(
        @Body postUnsubscription: PostUnsubscriptionRequest
    ): Maybe<Void>
}