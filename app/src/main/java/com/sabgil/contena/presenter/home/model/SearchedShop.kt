package com.sabgil.contena.presenter.home.model

import com.sabgil.contena.domain.model.Shop

data class SearchedShop(
    var subscribed: Boolean,
    val shopName: String,
    val shopLogoUrl: String,
    val subscriberCount: Long
) {
    companion object {

        fun from(shop: Shop, subscribedShops: List<Shop>) =
            SearchedShop(
                subscribed = subscribedShops.find { subscribedShop ->
                    subscribedShop.shopName == shop.shopName
                } != null,
                shopName = shop.shopName,
                shopLogoUrl = shop.shopLogoUrl,
                subscriberCount = shop.subscriberCount
            )
    }
}