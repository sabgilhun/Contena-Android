package com.sabgil.contena.data.repository

import com.sabgil.contena.data.remote.contena.dto.PostSubscriptionRequest
import com.sabgil.contena.data.remote.contena.dto.PostUnsubscriptionRequest
import com.sabgil.contena.domain.model.SubscriptionResult
import io.reactivex.Single

interface SubscriptionRepository {

    fun postSubscription(postSubscriptionRequest: PostSubscriptionRequest): Single<SubscriptionResult>

    fun postUnsubscription(postUnsubscription: PostUnsubscriptionRequest): Single<SubscriptionResult>
}