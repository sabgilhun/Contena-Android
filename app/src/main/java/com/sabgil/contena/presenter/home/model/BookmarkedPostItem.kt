package com.sabgil.contena.presenter.home.model

import com.sabgil.contena.common.adapter.BaseItem
import com.sabgil.contena.domain.model.NewProduct
import com.sabgil.contena.domain.model.Post
import com.sabgil.contena.utils.DateUtils

data class BookmarkedPostItem(
    val postId: Long,
    val diffDate: String,
    val uploadDate: String,
    val shopName: String,
    val shopLogoUrl: String,
    val subscriberCount: String,
    val newProductItems: List<NewProductItem>
) : BaseItem(postId) {

    val productSize
        get() = newProductItems.size

    data class NewProductItem(
        val productId: Long,
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
                    productId = from.productId,
                    productName = from.productName,
                    brand = from.brand,
                    imageUrl = from.imageUrl,
                    pageUrl = from.pageUrl,
                    price = from.price,
                    originPrice = from.originPrice
                )

            fun toNewProduct(from: NewProductItem): NewProduct =
                NewProduct(
                    productId = from.productId,
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
        fun from(from: Post): BookmarkedPostItem =
            BookmarkedPostItem(
                postId = from.postId,
                diffDate = from.uploadDate,  // TODO 변경할 예정
                uploadDate = from.uploadDate,  // TODO 변경할 예정
                shopName = from.shopName,
                shopLogoUrl = from.shopLogoUrl,
                subscriberCount = from.subscriberCount.toString(),
                newProductItems = from.newProductList.map { NewProductItem.from(it) }
            )

        fun toPost(from: BookmarkedPostItem): Post =
            Post(
                postId = from.postId,
                uploadDate = from.uploadDate,
                shopName = from.shopName,
                shopLogoUrl = from.shopLogoUrl,
                subscriberCount = null,
                newProductList = from.newProductItems.map { NewProductItem.toNewProduct(it) }
            )
    }
}
