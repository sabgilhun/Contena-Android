package com.sabgil.contena.presenter.home.model

import com.sabgil.contena.domain.model.Post
import com.sabgil.contena.domain.model.SummaryNewItem

data class PostListItem(
    var viewingPosition: Int = 0,
    val postId: Long,
    val diffDate: String,
    val uploadDate: String,
    val shopName: String,
    val shopLogoUrl: String,
    val newItemList: List<SummaryNewItem>,
    val hasMoreItem: Boolean
) {
    companion object {
        fun from(post: Post): PostListItem =
            PostListItem(
                postId = post.postId,
                diffDate = post.uploadDate,  // TODO 변경할 예정
                uploadDate = post.uploadDate,  // TODO 변경할 예정
                shopName = post.shopName,
                shopLogoUrl = post.shopLogoUrl,
                newItemList = post.newItemList,
                hasMoreItem = post.newItemList.size > 4
            )
    }
}