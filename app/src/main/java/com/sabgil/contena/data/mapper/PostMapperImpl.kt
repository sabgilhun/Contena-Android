package com.sabgil.contena.data.mapper

import com.sabgil.contena.data.remote.contena.dto.GetPostListResponse
import com.sabgil.contena.domain.model.Post
import javax.inject.Inject

class PostMapperImpl @Inject constructor() : PostMapper {

    override fun toPairOfCursorAndPostList(from: GetPostListResponse): Pair<Long, List<Post>> =
        from.lastCursor to from.postList.toPostList()

    private fun List<GetPostListResponse.Post>.toPostList(): List<Post> =
        map { post ->
            Post(
                postId = post.postId,
                uploadDate = post.uploadDate,
                shopName = post.shopName,
                shopLogoUrl = post.shopLogoUrl,
                subscriberCount = post.subscriberCount,
                newItemList = post.newItemList.map { item ->
                    Post.NewItem(
                        productName = item.productName,
                        brand = item.brand,
                        imageUrl = item.imageUrl,
                        pageUrl = item.pageUrl,
                        price = item.price,
                        originPrice = item.originPrice
                    )
                }
            )
        }
}