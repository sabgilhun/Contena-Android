package com.sabgil.contena.data.mapper

import com.sabgil.contena.data.remote.contena.dto.GetNewItemListResponse
import com.sabgil.contena.domain.model.NewProduct
import javax.inject.Inject

class NewItemMapperImpl @Inject constructor() : NewItemMapper {

    override fun toNewItemList(from: GetNewItemListResponse): List<NewProduct> =
        from.newItemList.map {
            NewProduct(
                productName = it.productName,
                brand = it.brand,
                imageUrl = it.imageUrl,
                pageUrl = it.pageUrl,
                price = it.price,
                originPrice = it.originPrice
            )
        }
}