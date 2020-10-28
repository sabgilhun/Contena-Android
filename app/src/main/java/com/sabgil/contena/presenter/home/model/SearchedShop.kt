package com.sabgil.contena.presenter.home.model

import com.sabgil.contena.common.adapter.BaseItem
import com.sabgil.contena.domain.model.Shop
import com.sabgil.contena.domain.model.SubscriptionResult

data class SearchedShop(
    var isSubscribed: Boolean,
    val shopName: String,
    val shopLogoUrl: String,
    val subscriberCount: Long,
    val shopDescription: String,
    var isLoading: Boolean = false
) : BaseItem(shopName) {
    companion object {

        fun from(shop: Shop) =
            SearchedShop(
                isSubscribed = shop.isSubscribed,
                shopName = shop.shopName,
                shopLogoUrl = shop.shopLogoUrl,
                subscriberCount = shop.subscriberCount,
                shopDescription = shop.shopDescription
            )

        fun from(subscriptionResult: SubscriptionResult): SearchedShop {
            val shop = subscriptionResult.updatedShop
            return SearchedShop(
                isSubscribed = shop.isSubscribed,
                shopName = shop.shopName,
                shopLogoUrl = shop.shopLogoUrl,
                subscriberCount = shop.subscriberCount,
                shopDescription = shop.shopDescription
            )
        }
    }
}