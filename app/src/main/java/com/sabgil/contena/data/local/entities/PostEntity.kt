package com.sabgil.contena.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sabgil.contena.domain.model.NewProduct
import com.sabgil.contena.domain.model.Post

@Entity(tableName = "POST")
data class PostEntity constructor(

    @PrimaryKey
    @ColumnInfo(name = "post_id")
    val postId: Long,

    @ColumnInfo(name = "upload_date")
    val uploadDate: String,

    @ColumnInfo(name = "shop_name")
    val shopName: String,

    @ColumnInfo(name = "shop_logo_url")
    val shopLogoUrl: String,

    @ColumnInfo(name = "new_product_list")
    val newProductList: List<NewProduct>
) {

    companion object {

        fun from(from: Post) =
            PostEntity(
                postId = from.postId,
                uploadDate = from.uploadDate,
                shopName = from.shopName,
                shopLogoUrl = from.shopLogoUrl,
                newProductList = from.newProductList
            )
    }
}