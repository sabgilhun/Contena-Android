package com.sabgil.contena.domain.model

data class Subscription(
    val subscribed: Boolean,
    val userId: String,
    val shopName: String
)