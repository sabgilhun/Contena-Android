package com.sabgil.contena.domain.model

data class SubscriptionResult(
    val userId: String,
    val updatedShop: Shop
) {
    data class Shop(
        val shopName: String,
        val shopLogoUrl: String,
        val subscriberCount: Long,
        val shopDescription: String,
        val isSubscribed: Boolean
    )
}