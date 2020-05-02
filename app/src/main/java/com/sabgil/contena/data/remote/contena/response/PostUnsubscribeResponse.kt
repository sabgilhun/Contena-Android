package com.sabgil.contena.data.remote.contena.response

import com.google.gson.annotations.SerializedName

data class PostUnsubscribeResponse(
    @SerializedName("user_id") var userId: String,
    @SerializedName("shop_name") var shopName: String
)