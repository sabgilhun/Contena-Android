package com.sabgil.contena.data.mapper

import com.sabgil.contena.data.remote.contena.dto.PostSubscribeResponse
import com.sabgil.contena.data.remote.contena.dto.PostUnsubscribeResponse
import com.sabgil.contena.domain.model.Shop
import com.sabgil.contena.domain.model.SubscriptionResult
import javax.inject.Inject

class SubscriptionMapperImpl @Inject constructor() : SubscriptionMapper {

    override fun toSubscriptionResult(from: PostSubscribeResponse): SubscriptionResult =
        SubscriptionResult(
            userId = from.userId,
            updatedShop = toShop(from.updatedShop)
        )

    override fun toSubscriptionResult(from: PostUnsubscribeResponse): SubscriptionResult =
        SubscriptionResult(
            userId = from.userId,
            updatedShop = toShop(from.updatedShop)
        )

    private fun toShop(from: PostSubscribeResponse.Shop) =
        Shop(
            from.shopName,
            from.shopLogoUrl,
            from.subscriberCount,
            from.shopDescription,
            from.isSubscribed
        )

    private fun toShop(from: PostUnsubscribeResponse.Shop) =
        Shop(
            from.shopName,
            from.shopLogoUrl,
            from.subscriberCount,
            from.shopDescription,
            from.isSubscribed
        )
}