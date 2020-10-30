package com.sabgil.contena.presenter.home.model

import com.sabgil.contena.R
import com.sabgil.contena.common.adapter.BaseItem
import com.sabgil.contena.domain.model.Shop
import com.sabgil.contena.domain.model.SubscriptionResult

sealed class BaseSearchedShopItem(key: Any) : BaseItem(key) {
    data class ShopItem(
        var isSubscribed: Boolean,
        val shopName: String,
        val shopLogoUrl: String,
        val subscriberCount: Long,
        val shopDescription: String,
        var isLoading: Boolean = false
    ) : BaseSearchedShopItem(shopName) {

        val loadingBarColor = if (isSubscribed) R.color.colorBeigeWhite else R.color.colorBlack

        companion object {
            fun from(shop: Shop) =
                ShopItem(
                    isSubscribed = shop.isSubscribed,
                    shopName = shop.shopName,
                    shopLogoUrl = shop.shopLogoUrl,
                    subscriberCount = shop.subscriberCount,
                    shopDescription = shop.shopDescription
                )

            fun from(subscriptionResult: SubscriptionResult): ShopItem {
                val shop = subscriptionResult.updatedShop
                return ShopItem(
                    isSubscribed = shop.isSubscribed,
                    shopName = shop.shopName,
                    shopLogoUrl = shop.shopLogoUrl,
                    subscriberCount = shop.subscriberCount,
                    shopDescription = shop.shopDescription
                )
            }
        }
    }

    object EmptyItem : BaseSearchedShopItem(EmptyItem::class.java.simpleName)
}

