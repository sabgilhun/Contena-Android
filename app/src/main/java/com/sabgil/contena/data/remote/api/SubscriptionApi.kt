package com.sabgil.contena.data.remote.api

import com.sabgil.contena.data.remote.dto.PostSubscribeResponse
import com.sabgil.contena.data.remote.dto.PostSubscriptionRequest
import com.sabgil.contena.data.remote.dto.PostUnsubscribeResponse
import com.sabgil.contena.data.remote.dto.PostUnsubscriptionRequest
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface SubscriptionApi {

    @POST(value = "subscription")
    fun postSubscription(
        @Body postSubscriptionRequest: PostSubscriptionRequest
    ): Single<PostSubscribeResponse>

    @POST(value = "unsubscription")
    fun postUnsubscription(
        @Body postUnsubscription: PostUnsubscriptionRequest
    ): Single<PostUnsubscribeResponse>
}