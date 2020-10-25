package com.sabgil.contena.data.mapper

import com.sabgil.contena.data.remote.contena.dto.GetAllShopListResponse
import com.sabgil.contena.data.remote.contena.dto.GetAvailableShopListResponse
import com.sabgil.contena.data.remote.contena.dto.GetRecommendShopListResponse
import com.sabgil.contena.data.remote.contena.dto.GetSubscriptionShopListResponse
import com.sabgil.contena.domain.model.Shop
import javax.inject.Inject

class ShopMapperImpl @Inject constructor() : ShopMapper {

    override fun toShopList(from: GetAllShopListResponse): List<Shop> =
        from.shopList.map {
            Shop(
                shopName = it.shopName,
                shopLogoUrl = it.shopLogoUrl,
                subscriberCount = it.subscriberCount,
                shopDescription = it.shopDescription,
                isSubscribed = it.isSubscribed
            )
        }

    override fun toShopList(from: GetRecommendShopListResponse): List<Shop> =
        from.shopList.map {
            Shop(
                shopName = it.shopName,
                shopLogoUrl = it.shopLogoUrl,
                subscriberCount = it.subscriberCount,
                shopDescription = it.shopDescription,
                isSubscribed = it.isSubscribed
            )
        }

    override fun toShopList(from: GetAvailableShopListResponse): List<Shop> =
        from.shopList.map {
            Shop(
                shopName = it.shopName,
                shopLogoUrl = it.shopLogoUrl,
                subscriberCount = it.subscriberCount,
                shopDescription = it.shopDescription,
                isSubscribed = it.isSubscribed
            )
        }

    override fun toShopList(from: GetSubscriptionShopListResponse): List<Shop> =
        from.shopList.map {
            Shop(
                shopName = it.shopName,
                shopLogoUrl = it.shopLogoUrl,
                subscriberCount = it.subscriberCount,
                shopDescription = it.shopDescription,
                isSubscribed = it.isSubscribed
            )
        }
}