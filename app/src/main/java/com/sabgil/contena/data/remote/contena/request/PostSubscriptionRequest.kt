package com.sabgil.contena.data.remote.contena.request

import com.google.gson.annotations.SerializedName

data class PostSubscriptionRequest(
    @SerializedName("user_id") var userId: String,
    @SerializedName("shop_name") var shopName: String
)