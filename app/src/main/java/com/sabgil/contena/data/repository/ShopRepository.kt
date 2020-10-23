package com.sabgil.contena.data.repository

import com.sabgil.contena.domain.model.Shop
import io.reactivex.Single

interface ShopRepository {

    fun getAllShopList(): Single<List<Shop>>

    fun getRecommendShopList(): Single<List<Shop>>

    fun getAvailableShopList(searchKeyword: String): Single<List<Shop>>

    fun getSubscriptionShopList(userId: String): Single<List<Shop>>
}