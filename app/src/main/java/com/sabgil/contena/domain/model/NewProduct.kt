package com.sabgil.contena.domain.model

data class NewProduct(
    val productId: Long,
    val productName: String,
    val brand: String,
    val imageUrl: String,
    val pageUrl: String,
    val price: String,
    val originPrice: String?
)