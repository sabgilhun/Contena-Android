package com.sabgil.contena.data.repository

import com.sabgil.contena.domain.model.DetailNewItem
import com.sabgil.contena.domain.model.Post
import com.sabgil.contena.domain.model.Shop
import com.sabgil.contena.domain.model.Subscription
import io.reactivex.Single

interface ContenaRepository {

    fun getPostList(userId: String, cursor: Long? = null): Single<Pair<List<Post>, Long>>

    fun getNewItemList(postId: Long): Single<List<DetailNewItem>>

    fun getAvailableShopList(searchKeyword: String): Single<List<Shop>>

    fun getSubscribedShopList(userId: String): Single<List<Shop>>

    fun postSubscription(userId: String, shopName: String): Single<Subscription>

    fun postUnsubscription(userId: String, shopName: String): Single<Subscription>
}