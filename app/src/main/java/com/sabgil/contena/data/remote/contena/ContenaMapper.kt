package com.sabgil.contena.data.remote.contena

import com.sabgil.contena.data.remote.contena.response.*
import com.sabgil.contena.domain.model.*
import com.sabgil.contena.utils.DateUtils
import javax.inject.Inject

class ContenaMapper @Inject constructor() {

    fun toNewItemList(getNewItemListResponse: GetNewItemListResponse): List<DetailNewItem> =
        getNewItemListResponse.newItemList.map {
            DetailNewItem(
                productName = it.productName,
                brandName = it.brand,
                imageUrl = it.imageUrl,
                landingPageUrl = it.pageUrl,
                price = it.price
            )
        }

    fun toPostAndCursor(getPostListResponse: GetPostListResponse): Pair<List<Post>, Long> =
        Pair(
            first = getPostListResponse.postList.map { postData ->
                Post(
                    postId = postData.postId,
                    uploadDate = DateUtils.parseContenaToHangul(postData.uploadDate),
                    shopName = postData.shopName,
                    shopLogoUrl = postData.shopLogoUrl,
                    newItemList = postData.newItemList.map { newItemData ->
                        SummaryNewItem(
                            productName = newItemData.productName,
                            brandName = newItemData.brand,
                            imageUrl = newItemData.imageUrl,
                            price = newItemData.price
                        )
                    }
                )
            },
            second = getPostListResponse.lastCursor
        )

    fun toShopList(getShopListResponse: GetShopListResponse): List<Shop> =
        getShopListResponse.shopList.map {
            Shop(
                shopName = it.shopName,
                shopLogoUrl = it.shopLogoUrl,
                subscriberCount = it.subscriberCount
            )
        }

    fun toSubscription(postSubscribeResponse: PostSubscribeResponse): Subscription =
        postSubscribeResponse.let {
            Subscription(
                subscribed = true,
                shopName = it.shopName,
                userId = it.userId
            )
        }

    fun toSubscription(postUnsubscribeResponse: PostUnsubscribeResponse): Subscription =
        postUnsubscribeResponse.let {
            Subscription(
                subscribed = false,
                shopName = it.shopName,
                userId = it.userId
            )
        }
}