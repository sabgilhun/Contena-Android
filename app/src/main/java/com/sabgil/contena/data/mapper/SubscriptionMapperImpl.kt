package com.sabgil.contena.data.mapper

import com.sabgil.contena.data.remote.contena.dto.PostSubscribeResponse
import com.sabgil.contena.data.remote.contena.dto.PostUnsubscribeResponse
import com.sabgil.contena.domain.model.SubscriptionResult
import javax.inject.Inject

class SubscriptionMapperImpl @Inject constructor() : SubscriptionMapper {

    override fun toSubscriptionResult(from: PostSubscribeResponse): SubscriptionResult =
        SubscriptionResult(
            userId = from.userId,
            shopName = from.shopName,
            subscriberCount = from.subscriberCount
        )

    override fun toSubscriptionResult(from: PostUnsubscribeResponse): SubscriptionResult =
        SubscriptionResult(
            userId = from.userId,
            shopName = from.shopName,
            subscriberCount = from.subscriberCount
        )
}