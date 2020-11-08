package com.sabgil.contena.data.mapper

import com.sabgil.contena.data.local.entities.NewProductEntity
import com.sabgil.contena.data.remote.dto.GetNewItemListResponse
import com.sabgil.contena.domain.model.NewProduct
import javax.inject.Inject

class NewProductMapperImpl @Inject constructor() : NewProductMapper {

    override fun toNewProducts(from: GetNewItemListResponse): List<NewProduct> =
        from.newItemList.map {
            NewProduct(
                productId = it.itemId,
                productName = it.productName,
                brand = it.brand,
                imageUrl = it.imageUrl,
                pageUrl = it.pageUrl,
                price = it.price,
                originPrice = it.originPrice
            )
        }

    override fun toNewProduct(from: NewProductEntity): NewProduct = NewProduct(
        productId = from.productId,
        productName = from.productName,
        brand = from.brand,
        imageUrl = from.imageUrl,
        pageUrl = from.pageUrl,
        price = from.price,
        originPrice = from.originPrice
    )
}