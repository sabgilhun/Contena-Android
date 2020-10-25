package com.sabgil.contena.domain.model

data class Shop(
    val shopName: String,
    val shopLogoUrl: String,
    val subscriberCount: Long,
    val shopDescription: String,
    val isSubscribed: Boolean
)