package com.sabgil.contena.domain.model

data class Post(
    val postId: Long,
    val uploadDate: String,
    val shopName: String,
    val shopLogoUrl: String,
    val subscriberCount: Long,
    val newItemList: List<NewItem>
) {
    data class NewItem(
        val productName: String,
        val brand: String,
        val imageUrl: String,
        val pageUrl: String,
        val price: String
    )
}