package com.sabgil.contena.data.repository

import com.sabgil.contena.data.remote.contena.api.SubscriptionApi
import com.sabgil.contena.data.remote.contena.dto.PostSubscriptionRequest
import com.sabgil.contena.data.remote.contena.dto.PostUnsubscriptionRequest
import com.sabgil.contena.domain.model.SubscriptionResult
import io.reactivex.Single
import javax.inject.Inject


class SubscriptionRepositoryImpl @Inject constructor(
    private val subscriptionApi: SubscriptionApi
) : SubscriptionRepository {

    override fun postSubscription(postSubscriptionRequest: PostSubscriptionRequest): Single<SubscriptionResult> {
        TODO("Not yet implemented")
    }

    override fun postUnsubscription(postUnsubscription: PostUnsubscriptionRequest): Single<SubscriptionResult> {
        TODO("Not yet implemented")
    }
}