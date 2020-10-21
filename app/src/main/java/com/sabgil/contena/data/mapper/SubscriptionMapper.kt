package com.sabgil.contena.data.mapper

import com.sabgil.contena.data.remote.contena.dto.PostSubscribeResponse
import com.sabgil.contena.data.remote.contena.dto.PostUnsubscribeResponse
import com.sabgil.contena.domain.model.SubscriptionResult

interface SubscriptionMapper {

    fun toSubscriptionResult(from: PostSubscribeResponse): SubscriptionResult

    fun toSubscriptionResult(from: PostUnsubscribeResponse): SubscriptionResult
}