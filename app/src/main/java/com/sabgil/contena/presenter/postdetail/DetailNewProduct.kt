package com.sabgil.contena.presenter.postdetail

import com.sabgil.contena.common.adapter.BaseItem
import com.sabgil.contena.domain.model.NewProduct

data class DetailNewProduct(
    val productId: Long,
    val productName: String,
    val brand: String,
    val imageUrl: String,
    val pageUrl: String,
    val price: String,
    val isBookmarked: Boolean,
    val originPrice: String?
) : BaseItem(productId) {

    companion object {
        fun from(from: NewProduct, bookmarkedIds: Set<Long>) = DetailNewProduct(
            productId = from.productId,
            productName = from.productName,
            brand = from.brand,
            imageUrl = from.imageUrl,
            pageUrl = from.pageUrl,
            price = from.price,
            isBookmarked = bookmarkedIds.contains(from.productId),
            originPrice = from.originPrice
        )

        fun toNewProduct(from: DetailNewProduct) = NewProduct(
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