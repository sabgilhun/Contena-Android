package com.sabgil.contena.data.repository

import com.sabgil.contena.data.mapper.ShopMapper
import com.sabgil.contena.data.remote.contena.api.ShopApi
import com.sabgil.contena.domain.model.Shop
import io.reactivex.Single
import javax.inject.Inject


class ShopRepositoryImpl @Inject constructor(
    private val shopApi: ShopApi,
    private val shopMapper: ShopMapper
) : ShopRepository {

    override fun getAllShopList(userId: String): Single<List<Shop>> =
        shopApi.getAllShopList(userId).map<List<Shop>>(shopMapper::toShopList)

    override fun getRecommendShopList(userId: String): Single<List<Shop>> =
        shopApi.getRecommendShopList(userId).map<List<Shop>>(shopMapper::toShopList)

    override fun getAvailableShopList(userId: String, searchKeyword: String): Single<List<Shop>> =
        shopApi.getAvailableShopList(userId, searchKeyword).map<List<Shop>>(shopMapper::toShopList)

    override fun getSubscriptionShopList(userId: String): Single<List<Shop>> =
        shopApi.getSubscriptionShopList(userId).map<List<Shop>>(shopMapper::toShopList)
}