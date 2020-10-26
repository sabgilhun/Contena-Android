package com.sabgil.contena.presenter.home.model

import com.sabgil.contena.common.adapter.BaseItem
import com.sabgil.contena.domain.model.Post
import java.util.concurrent.atomic.AtomicBoolean

data class PostItem(
    val postId: Long,
    val diffDate: String,
    val uploadDate: String,
    val shopName: String,
    val shopLogoUrl: String,
    val subscriberCount: String,
    val newItemList: List<Post.NewItem>
) : BaseItem(postId) {
    companion object {
        @JvmStatic
        fun from(post: Post): PostItem =
            PostItem(
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

class LoadingItem(
    val nextCursor: Long,
    var statedLoading: AtomicBoolean = AtomicBoolean(false)
) : BaseItem(LoadingItem::class.java.simpleName)

object EmptyItem : BaseItem(EmptyItem::class.java.simpleName)
object NoMoreItem : BaseItem(NoMoreItem::class.java.simpleName)
