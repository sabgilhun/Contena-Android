package com.sabgil.contena.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PostSubscriptionRequest(
    @SerializedName("user_id") val userId: String,
    @SerializedName("shop_name") val shopName: String
)

data class PostSubscribeResponse(
    @SerializedName("user_id") val userId: String,
    @SerializedName("updated_shop") val updatedShop: Shop
) {
    data class Shop(
        @SerializedName("shop_name") val shopName: String,
        @SerializedName("shop_logo_url") val shopLogoUrl: String,
        @SerializedName("subscriber_count") val subscriberCount: Long,
        @SerializedName("shop_description") val shopDescription: String,
        @SerializedName("subscribed") val isSubscribed: Boolean
    )
}

data class PostUnsubscriptionRequest(
    @SerializedName("user_id") val userId: String,
    @SerializedName("shop_name") val shopName: String
)

data class PostUnsubscribeResponse(
    @SerializedName("user_id") val userId: String,
    @SerializedName("updated_shop") val updatedShop: Shop
) {
    data class Shop(
        @SerializedName("shop_name") val shopName: String,
        @SerializedName("shop_logo_url") val shopLogoUrl: String,
        @SerializedName("subscriber_count") val subscriberCount: Long,
        @SerializedName("shop_description") val shopDescription: String,
        @SerializedName("subscribed") val isSubscribed: Boolean
    )
}