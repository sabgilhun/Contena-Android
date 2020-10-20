package com.sabgil.contena.data.repository

import com.sabgil.contena.data.remote.contena.api.ShopApi
import com.sabgil.contena.domain.model.Shop
import io.reactivex.Single
import javax.inject.Inject


class ShopRepositoryImpl @Inject constructor(
    private val shopApi: ShopApi
) : ShopRepository {

    override fun getRecommendShopList(): Single<List<Shop>> {
        TODO("Not yet implemented")
    }

    override fun getAvailableShopList(searchKeyword: String): Single<List<Shop>> {
        TODO("Not yet implemented")
    }

    override fun getSubscriptionShopList(userId: String): Single<List<Shop>> {
        TODO("Not yet implemented")
    }
}