package com.sabgil.contena.data.repository

import com.sabgil.contena.data.remote.contena.ContenaApi
import com.sabgil.contena.data.remote.contena.ContenaMapper
import com.sabgil.contena.domain.model.DetailNewItem
import com.sabgil.contena.domain.model.Post
import com.sabgil.contena.domain.model.Shop
import com.sabgil.contena.domain.model.Subscription
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

class ContenaRepositoryImpl @Inject constructor(
    private val contenaApi: ContenaApi,
    private val contenaMapper: ContenaMapper
) : ContenaRepository {

    override fun getPostList(userId: String, cursor: Long?): Single<Pair<List<Post>, Long>> =
        contenaApi.getPostList(userId, cursor)
            .map(contenaMapper::toPostAndCursor)

    override fun getNewItemList(postId: Long): Single<List<DetailNewItem>> =
        contenaApi.getNewItemList(postId)
            .map(contenaMapper::toNewItemList)

    override fun getAvailableShopList(searchKeyword: String): Single<List<Shop>> =
        contenaApi.getAvailableShopList(searchKeyword)
            .map(contenaMapper::toShopList)

    override fun getSubscribedShopList(userId: String): Single<List<Shop>> =
        contenaApi.getSubscribedShopList(userId)
            .map(contenaMapper::toShopList)

    override fun postSubscription(subscription: Subscription): Maybe<Void> =
        contenaApi.postSubscription(contenaMapper.toPostSubscriptionRequest(subscription))

    override fun postUnsubscription(subscription: Subscription): Maybe<Void> =
        contenaApi.postUnsubscription(contenaMapper.toPostUnsubscriptionRequest(subscription))
}