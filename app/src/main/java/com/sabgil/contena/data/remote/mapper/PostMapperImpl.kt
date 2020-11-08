package com.sabgil.contena.data.remote.mapper

import com.sabgil.contena.data.remote.dto.GetPostListResponse
import com.sabgil.contena.domain.model.NewProduct
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
                newProductList = post.newItemList.map { item ->
                    NewProduct(
                        // TODO : delete mock data
                        productId = 0,
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