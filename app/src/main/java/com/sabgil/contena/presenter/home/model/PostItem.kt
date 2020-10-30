package com.sabgil.contena.presenter.home.model

import com.sabgil.contena.common.adapter.BaseItem
import java.util.concurrent.atomic.AtomicBoolean
import com.sabgil.contena.domain.model.Post as DomainPost
import com.sabgil.contena.domain.model.Post.NewProduct as DomainNewProduct

sealed class PostItem(key: Any) : BaseItem(key) {

    data class Post(
        val postId: Long,
        val diffDate: String,
        val uploadDate: String,
        val shopName: String,
        val shopLogoUrl: String,
        val subscriberCount: String,
        val newItemList: List<NewProduct>,
        var displayingItemIndex: Int = 0
    ) : PostItem(postId) {

        data class NewProduct(
            val productName: String,
            val brand: String,
            val imageUrl: String,
            val pageUrl: String,
            val price: String,
            val originPrice: String?
        ) {
            companion object {
                fun from(from: DomainNewProduct) =
                    NewProduct(
                        productName = from.productName,
                        brand = from.brand,
                        imageUrl = from.imageUrl,
                        pageUrl = from.pageUrl,
                        price = from.price,
                        originPrice = from.originPrice
                    )
            }
        }

        companion object {
            fun from(from: DomainPost): Post =
                Post(
                    postId = from.postId,
                    diffDate = from.uploadDate,  // TODO 변경할 예정
                    uploadDate = from.uploadDate,  // TODO 변경할 예정
                    shopName = from.shopName,
                    shopLogoUrl = from.shopLogoUrl,
                    subscriberCount = from.subscriberCount.toString(),
                    newItemList = from.newProductList.map { NewProduct.from(it) }
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
