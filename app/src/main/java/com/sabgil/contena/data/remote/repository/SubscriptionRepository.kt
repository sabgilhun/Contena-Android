package com.sabgil.contena.data.remote.repository

import com.sabgil.contena.data.remote.dto.PostSubscriptionRequest
import com.sabgil.contena.data.remote.dto.PostUnsubscriptionRequest
import com.sabgil.contena.domain.model.SubscriptionResult
import io.reactivex.Single

interface SubscriptionRepository {

    fun postSubscription(postSubscriptionRequest: PostSubscriptionRequest): Single<SubscriptionResult>

    fun postUnsubscription(postUnsubscription: PostUnsubscriptionRequest): Single<SubscriptionResult>
}