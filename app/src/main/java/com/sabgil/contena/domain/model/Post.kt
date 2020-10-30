package com.sabgil.contena.domain.model

data class Post(
    val postId: Long,
    val uploadDate: String,
    val shopName: String,
    val shopLogoUrl: String,
    val subscriberCount: Long,
    val newProductList: List<NewProduct>
) {
    data class NewProduct(
        val productName: String,
        val brand: String,
        val imageUrl: String,
        val pageUrl: String,
        val price: String,
        val originPrice: String?
    )
}