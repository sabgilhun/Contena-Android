package com.sabgil.contena.data.remote.contena

import com.sabgil.contena.data.remote.contena.request.DeleteSubscriptionRequest
import com.sabgil.contena.data.remote.contena.request.PostSubscriptionRequest
import com.sabgil.contena.data.remote.contena.response.GetNewItemListResponse
import com.sabgil.contena.data.remote.contena.response.GetPostListResponse
import com.sabgil.contena.data.remote.contena.response.GetShopListResponse
import io.reactivex.Single
import retrofit2.http.*

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

    @POST
    fun postSubscription(
        @Body postSubscriptionRequest: PostSubscriptionRequest
    ): Single<Void>

    @DELETE
    fun deleteSubscription(
        @Body deleteSubscriptionRequest: DeleteSubscriptionRequest
    ): Single<Void>
}