package com.sabgil.contena.data.remote.contena

import com.sabgil.contena.common.pagemanager.PageHolder
import com.sabgil.contena.data.remote.contena.request.PostSubscriptionRequest
import com.sabgil.contena.data.remote.contena.request.PostUnsubscriptionRequest
import com.sabgil.contena.data.remote.contena.response.GetNewItemListResponse
import com.sabgil.contena.data.remote.contena.response.GetPostListResponse
import com.sabgil.contena.data.remote.contena.response.GetShopListResponse
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

    fun toNewItemPostPage(getPostListResponse: GetPostListResponse): PageHolder<Post> =
        PageHolder(
            items = getPostListResponse.postList.map { postData ->
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
            cursor = getPostListResponse.lastCursor
        )

    fun toShopList(getShopListResponse: GetShopListResponse): List<Shop> =
        getShopListResponse.availableShopList.map {
            Shop(
                shopName = it.shopName,
                shopLogoUrl = it.shopLogoUrl,
                subscriberCount = it.subscriberCount
            )
        }

    fun toPostSubscriptionRequest(subscription: Subscription): PostSubscriptionRequest =
        subscription.let {
            PostSubscriptionRequest(it.userId, it.shopName)
        }

    fun toPostUnsubscriptionRequest(subscription: Subscription): PostUnsubscriptionRequest =
        subscription.let {
            PostUnsubscriptionRequest(it.userId, it.shopName)
        }
}