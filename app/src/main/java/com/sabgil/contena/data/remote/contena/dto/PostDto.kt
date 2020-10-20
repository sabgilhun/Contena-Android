package com.sabgil.contena.data.remote.contena.dto

import com.google.gson.annotations.SerializedName

data class GetPostListResponse(
    @SerializedName("last_cursor") val lastCursor: Long,
    @SerializedName("post_list") val postList: List<Post>
) {
    data class Post(
        @SerializedName("post_id") val postId: Long,
        @SerializedName("upload_data") val uploadDate: String,
        @SerializedName("shop_name") val shopName: String,
        @SerializedName("shop_logo_url") val shopLogoUrl: String,
        @SerializedName("subscriber_count") val subscriberCount: Long,
        @SerializedName("new_item_list") val newItemList: List<NewItem>
    )

    data class NewItem(
        @SerializedName("product_name") val productName: String,
        @SerializedName("brand") val brand: String,
        @SerializedName("image_url") val imageUrl: String,
        @SerializedName("page_url") val pageUrl: String,
        @SerializedName("price") val price: String,
        @SerializedName("origin_price") val originPrice: String?
    )
}