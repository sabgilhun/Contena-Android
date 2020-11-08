package com.sabgil.contena.data.remote.repository

import com.sabgil.contena.domain.model.Shop
import io.reactivex.Single

interface ShopRepository {

    fun getAllShopList(userId: String): Single<List<Shop>>

    fun getRecommendShopList(userId: String): Single<List<Shop>>

    fun getAvailableShopList(userId: String, searchKeyword: String): Single<List<Shop>>

    fun getSubscriptionShopList(userId: String): Single<List<Shop>>
}