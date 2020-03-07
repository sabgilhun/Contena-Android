package com.sabgil.contena.data.remote.contena.response

import com.google.gson.annotations.SerializedName

data class GetShopListResponse(
    @SerializedName("available_shop_list") val availableShopList: List<AvailableShop>
) {
    data class AvailableShop(
        @SerializedName("shop_name") val shopName: String,
        @SerializedName("shop_logo_url") val shopLogoUrl: String,
        @SerializedName("subscriber_count") val subscriberCount: Long
    )
}