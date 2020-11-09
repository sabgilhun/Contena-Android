package com.sabgil.contena.presenter.home.model

import com.sabgil.contena.common.adapter.BaseItem
import com.sabgil.contena.domain.model.NewProduct


data class BookmarkedNewProductItem(
    val productId: Long,
    val productName: String,
    val brand: String,
    val imageUrl: String,
    val pageUrl: String,
    val price: String,
    val originPrice: String?
) : BaseItem(productId) {

    companion object {

        fun from(from: NewProduct) = BookmarkedNewProductItem(
            productId = from.productId,
            productName = from.productName,
            brand = from.brand,
            imageUrl = from.imageUrl,
            pageUrl = from.pageUrl,
            price = from.price,
            originPrice = from.originPrice
        )

        fun toNewProduct(from: BookmarkedNewProductItem) = NewProduct(
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

