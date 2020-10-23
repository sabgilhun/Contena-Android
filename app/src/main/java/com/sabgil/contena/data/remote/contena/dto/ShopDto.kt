package com.sabgil.contena.data.remote.contena.dto

import com.google.gson.annotations.SerializedName

data class GetAllShopListResponse(
    @SerializedName("shop_list") val shopList: List<Shop>
) {
    data class Shop(
        @SerializedName("shop_name") val shopName: String,
        @SerializedName("shop_logo_url") val shopLogoUrl: String,
        @SerializedName("subscriber_count") val subscriberCount: Long,
        @SerializedName("shop_description") val shopDescription: String
    )
}

data class GetRecommendShopListResponse(
    @SerializedName("shop_list") val shopList: List<Shop>
) {
    data class Shop(
        @SerializedName("shop_name") val shopName: String,
        @SerializedName("shop_logo_url") val shopLogoUrl: String,
        @SerializedName("subscriber_count") val subscriberCount: Long,
        @SerializedName("shop_description") val shopDescription: String
    )
}

data class GetAvailableShopListResponse(
    @SerializedName("shop_list") val shopList: List<Shop>
) {
    data class Shop(
        @SerializedName("shop_name") val shopName: String,
        @SerializedName("shop_logo_url") val shopLogoUrl: String,
        @SerializedName("subscriber_count") val subscriberCount: Long,
        @SerializedName("shop_description") val shopDescription: String
    )
}

data class GetSubscriptionShopListResponse(
    @SerializedName("shop_list") val shopList: List<Shop>
) {
    data class Shop(
        @SerializedName("shop_name") val shopName: String,
        @SerializedName("shop_logo_url") val shopLogoUrl: String,
        @SerializedName("subscriber_count") val subscriberCount: Long,
        @SerializedName("shop_description") val shopDescription: String
    )
}