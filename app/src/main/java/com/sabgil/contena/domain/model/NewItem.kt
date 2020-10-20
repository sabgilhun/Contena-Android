package com.sabgil.contena.domain.model

data class NewItem(
    val productName: String,
    val brand: String,
    val imageUrl: String,
    val pageUrl: String,
    val price: String,
    val originPrice: String?
)