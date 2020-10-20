package com.sabgil.contena.data.remote.contena.api

import com.sabgil.contena.data.remote.contena.dto.GetAvailableShopListResponse
import com.sabgil.contena.data.remote.contena.dto.GetRecommendShopListResponse
import com.sabgil.contena.data.remote.contena.dto.GetSubscriptionShopListResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ShopApi {

    @GET(value = "shop_list/recommend")
    fun getRecommendShopList(): Single<GetRecommendShopListResponse>

    @GET(value = "shop_list/available")
    fun getAvailableShopList(
        @Query(value = "search_keyword") searchKeyword: String
    ): Single<GetAvailableShopListResponse>

    @GET(value = "shop_list/subscription")
    fun getSubscriptionShopList(
        @Query(value = "user_id") userId: String
    ): Single<GetSubscriptionShopListResponse>
}