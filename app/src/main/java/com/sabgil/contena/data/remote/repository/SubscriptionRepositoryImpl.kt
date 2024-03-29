package com.sabgil.contena.data.remote.repository

import com.sabgil.contena.data.mapper.SubscriptionMapper
import com.sabgil.contena.data.remote.api.SubscriptionApi
import com.sabgil.contena.data.remote.dto.PostSubscriptionRequest
import com.sabgil.contena.data.remote.dto.PostUnsubscriptionRequest
import com.sabgil.contena.domain.model.SubscriptionResult
import io.reactivex.Single
import javax.inject.Inject


class SubscriptionRepositoryImpl @Inject constructor(
    private val subscriptionApi: SubscriptionApi,
    private val subscriptionMapper: SubscriptionMapper
) : SubscriptionRepository {

    override fun postSubscription(
        postSubscriptionRequest: PostSubscriptionRequest
    ): Single<SubscriptionResult> =
        subscriptionApi
            .postSubscription(postSubscriptionRequest)
            .map<SubscriptionResult>(subscriptionMapper::toSubscriptionResult)

    override fun postUnsubscription(
        postUnsubscription: PostUnsubscriptionRequest
    ): Single<SubscriptionResult> =
        subscriptionApi
            .postUnsubscription(postUnsubscription)
            .map<SubscriptionResult>(subscriptionMapper::toSubscriptionResult)
}