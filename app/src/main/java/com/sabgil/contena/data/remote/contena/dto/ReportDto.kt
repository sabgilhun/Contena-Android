package com.sabgil.contena.data.remote.contena.dto

import com.google.gson.annotations.SerializedName

data class PostReportRequest(
    @SerializedName("user_id") val userId: String,
    @SerializedName("post_id") val post_id: Long,
    @SerializedName("contents") val contents: String
)

data class PostReportResponse(
    @SerializedName("user_id") val userId: String,
    @SerializedName("post_id") val postId: Long,
    @SerializedName("contents") val contents: String,
    @SerializedName("reported") val reported: Boolean
)