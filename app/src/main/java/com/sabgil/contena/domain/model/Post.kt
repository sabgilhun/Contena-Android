package com.sabgil.contena.domain.model

data class Post(
    val postId: Long,
    val uploadDate: String,
    val shopName: String,
    val shopLogoUrl: String,
    val subscriberCount: Long?,
    val newProductList: List<NewProduct>
)