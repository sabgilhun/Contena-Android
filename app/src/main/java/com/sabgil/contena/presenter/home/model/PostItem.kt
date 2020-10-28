package com.sabgil.contena.presenter.home.model

import com.sabgil.contena.common.adapter.BaseItem
import java.util.concurrent.atomic.AtomicBoolean

sealed class PostItem(key: Any) : BaseItem(key) {

    data class Post(
        val postId: Long,
        val diffDate: String,
        val uploadDate: String,
        val shopName: String,
        val shopLogoUrl: String,
        val subscriberCount: String,
        val newItemList: List<com.sabgil.contena.domain.model.Post.NewItem>,
        var displayingItemIndex: Int = 0
    ) : PostItem(postId) {
        companion object {
            @JvmStatic
            fun from(post: com.sabgil.contena.domain.model.Post): PostItem =
                Post(
                    postId = post.postId,
                    diffDate = post.uploadDate,  // TODO 변경할 예정
                    uploadDate = post.uploadDate,  // TODO 변경할 예정
                    shopName = post.shopName,
                    shopLogoUrl = post.shopLogoUrl,
                    subscriberCount = post.subscriberCount.toString(),
                    newItemList = post.newItemList
                )
        }
    }

    class Loading(
        val nextCursor: Long,
        var statedLoading: AtomicBoolean = AtomicBoolean(false)
    ) : PostItem(Loading::class.java.simpleName)

    object Empty : PostItem(Empty::class.java.simpleName)

    object NoMore : PostItem(NoMore::class.java.simpleName)
}
