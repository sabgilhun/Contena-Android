package com.sabgil.contena.data.mapper

import com.sabgil.contena.data.remote.contena.dto.GetAvailableShopListResponse
import com.sabgil.contena.data.remote.contena.dto.GetRecommendShopListResponse
import com.sabgil.contena.data.remote.contena.dto.GetSubscriptionShopListResponse
import com.sabgil.contena.domain.model.Shop
import javax.inject.Inject

class ShopMapperImpl @Inject constructor() : ShopMapper {

    override fun toShopList(from: GetRecommendShopListResponse): List<Shop> {
        TODO("Not yet implemented")
    }

    override fun toShopList(from: GetAvailableShopListResponse): List<Shop> {
        TODO("Not yet implemented")
    }

    override fun toShopList(from: GetSubscriptionShopListResponse): List<Shop> {
        TODO("Not yet implemented")
    }
}