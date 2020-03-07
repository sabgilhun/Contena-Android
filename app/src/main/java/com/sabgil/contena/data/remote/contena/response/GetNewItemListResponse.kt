package com.sabgil.contena.data.remote.contena.response

import com.google.gson.annotations.SerializedName

data class GetNewItemListResponse(
    @SerializedName("new_item_list") val newItemList: List<NewItem>
) {
    data class NewItem(
        @SerializedName("product_name") val productName: String,
        @SerializedName("brand") val brand: String,
        @SerializedName("image_url") val imageUrl: String,
        @SerializedName("page_url") val pageUrl: String,
        @SerializedName("price") val price: String
    )
}