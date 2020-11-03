package com.sabgil.contena.presenter.home.model

import com.sabgil.contena.common.adapter.BaseItem
import com.sabgil.contena.domain.model.NewProduct
import com.sabgil.contena.domain.model.Post
import com.sabgil.contena.utils.DateUtils
import java.util.concurrent.atomic.AtomicBoolean

sealed class BasePostItem(key: Any) : BaseItem(key) {

    data class PostItem(
        val postId: Long,
        val diffDate: String,
        val uploadDate: String,
        val shopName: String,
        val shopLogoUrl: String,
        val subscriberCount: String,
        val newProductItems: List<NewProductItem>,
        var displayingItemIndex: Int = 0
    ) : BasePostItem(postId) {

        val pageNumber = "${displayingItemIndex + 1}/${newProductItems.size}"

        data class NewProductItem(
            val productName: String,
            val brand: String,
            val imageUrl: String,
            val pageUrl: String,
            val price: String,
            val originPrice: String?
        ) {
            companion object {
                fun from(from: NewProduct) =
                    NewProductItem(
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
            fun from(from: Post): PostItem =
                PostItem(
                    postId = from.postId,
                    diffDate = from.uploadDate,  // TODO 변경할 예정
                    uploadDate = DateUtils.parseContenaToHangul(from.uploadDate),  // TODO 변경할 예정
                    shopName = from.shopName,
                    shopLogoUrl = from.shopLogoUrl,
                    subscriberCount = from.subscriberCount.toString(),
                    newProductItems = from.newProductList.map { NewProductItem.from(it) }
                )
        }
    }

    class LoadingItem(
        val nextCursor: Long,
        var statedLoading: AtomicBoolean = AtomicBoolean(false)
    ) : BasePostItem(LoadingItem::class.java.simpleName)

    class LoadFailItem(
        val cursor: Long
    ) : BasePostItem(LoadingItem::class.java.simpleName)

    object EmptyItem : BasePostItem(EmptyItem::class.java.simpleName)

    object NoMoreItem : BasePostItem(NoMoreItem::class.java.simpleName)
}
