package com.sabgil.contena.data.mapper

import com.sabgil.contena.data.remote.contena.dto.PostSubscribeResponse
import com.sabgil.contena.data.remote.contena.dto.PostUnsubscribeResponse
import com.sabgil.contena.domain.model.SubscriptionResult
import javax.inject.Inject

class SubscriptionMapperImpl @Inject constructor() : SubscriptionMapper {

    override fun toSubscriptionResult(from: PostSubscribeResponse): SubscriptionResult {
        TODO("Not yet implemented")
    }

    override fun toSubscriptionResult(from: PostUnsubscribeResponse): SubscriptionResult {
        TODO("Not yet implemented")
    }
}