package com.sabgil.contena.data.repositories

import com.sabgil.contena.commons.pagemanager.PageHolder
import com.sabgil.contena.domain.model.DetailNewItem
import com.sabgil.contena.domain.model.Post
import com.sabgil.contena.domain.model.Shop
import com.sabgil.contena.domain.model.Subscription
import io.reactivex.Maybe
import io.reactivex.Single

interface ContenaRepository {

    fun getPostList(userId: String, cursor: Long? = null): Single<PageHolder<Post>>

    fun getNewItemList(postId: Long): Single<List<DetailNewItem>>

    fun getAvailableShopList(searchKeyword: String): Single<List<Shop>>

    fun getSubscribedShopList(userId: String): Single<List<Shop>>

    fun postSubscription(subscription: Subscription): Maybe<Void>

    fun postUnsubscription(subscription: Subscription): Maybe<Void>
}