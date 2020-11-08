package com.sabgil.contena.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sabgil.contena.domain.model.NewProduct

@Entity(tableName = "NEW_PRODUCT")
data class NewProductEntity constructor(

    @PrimaryKey
    @ColumnInfo(name = "product_id")
    val productId: Long,

    @ColumnInfo(name = "product_name")
    val productName: String,

    @ColumnInfo(name = "brand")
    val brand: String,

    @ColumnInfo(name = "image_url")
    val imageUrl: String,

    @ColumnInfo(name = "page_url")
    val pageUrl: String,

    @ColumnInfo(name = "price")
    val price: String,

    @ColumnInfo(name = "origin_price")
    val originPrice: String?
) {

    companion object {

        fun from(from: NewProduct) =
            NewProductEntity(
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