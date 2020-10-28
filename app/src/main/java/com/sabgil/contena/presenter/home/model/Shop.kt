package com.sabgil.contena.presenter.home.model

import com.sabgil.contena.R
import com.sabgil.contena.common.adapter.BaseItem
import com.sabgil.contena.domain.model.SubscriptionResult

sealed class SearchedShopItem(key: Any) : BaseItem(key) {
    data class Shop(
        var isSubscribed: Boolean,
        val shopName: String,
        val shopLogoUrl: String,
        val subscriberCount: Long,
        val shopDescription: String,
        var isLoading: Boolean = false
    ) : SearchedShopItem(shopName) {

        val loadingBarColor = if (isSubscribed) R.color.colorBeigeWhite else R.color.colorBlack

        companion object {
            fun from(shop: com.sabgil.contena.domain.model.Shop) =
                Shop(
                    isSubscribed = shop.isSubscribed,
                    shopName = shop.shopName,
                    shopLogoUrl = shop.shopLogoUrl,
                    subscriberCount = shop.subscriberCount,
                    shopDescription = shop.shopDescription
                )

            fun from(subscriptionResult: SubscriptionResult): Shop {
                val shop = subscriptionResult.updatedShop
                return Shop(
                    isSubscribed = shop.isSubscribed,
                    shopName = shop.shopName,
                    shopLogoUrl = shop.shopLogoUrl,
                    subscriberCount = shop.subscriberCount,
                    shopDescription = shop.shopDescription
                )
            }
        }
    }

    object Empty : SearchedShopItem(Empty::class.java.simpleName)
}

