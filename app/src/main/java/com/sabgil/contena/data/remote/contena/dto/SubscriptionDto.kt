package com.sabgil.contena.data.remote.contena.dto

import com.google.gson.annotations.SerializedName

data class PostSubscriptionRequest(
    @SerializedName("user_id") val userId: String,
    @SerializedName("shop_name") val shopName: String
)

data class PostSubscribeResponse(
    @SerializedName("user_id") val userId: String,
    @SerializedName("shop_name") val shopName: String,
    @SerializedName("subscriber_count") val subscriberCount: Long
)

data class PostUnsubscriptionRequest(
    @SerializedName("user_id") val userId: String,
    @SerializedName("shop_name") val shopName: String
)

data class PostUnsubscribeResponse(
    @SerializedName("user_id") val userId: String,
    @SerializedName("shop_name") val shopName: String,
    @SerializedName("subscriber_count") val subscriberCount: Long
)