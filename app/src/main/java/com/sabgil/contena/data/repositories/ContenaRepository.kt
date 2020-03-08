package com.sabgil.contena.data.repositories

import com.sabgil.contena.commons.PageHolder
import com.sabgil.contena.data.model.DetailNewItem
import com.sabgil.contena.data.model.NewItemPost
import com.sabgil.contena.data.model.Shop
import com.sabgil.contena.data.model.Subscription
import io.reactivex.Maybe
import io.reactivex.Single

interface ContenaRepository {

    fun getPostList(userId: String, cursor: Long? = null): Single<PageHolder<NewItemPost>>

    fun getNewItemList(postId: Long): Single<List<DetailNewItem>>

    fun getAvailableShopList(searchKeyword: String): Single<List<Shop>>

    fun getSubscribedShopList(userId: String): Single<List<Shop>>

    fun postSubscription(subscription: Subscription): Maybe<Void>

    fun deleteSubscription(subscription: Subscription): Maybe<Void>
}