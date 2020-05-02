package com.sabgil.contena.presenter.manage.model

import com.sabgil.contena.domain.model.Shop

data class SubscribedShop(
    var subscribed: Boolean,
    val shopName: String,
    val shopLogoUrl: String
)

fun Shop.toSubscribedShop() = SubscribedShop(
    subscribed = true,
    shopName = shopName,
    shopLogoUrl = shopLogoUrl
)