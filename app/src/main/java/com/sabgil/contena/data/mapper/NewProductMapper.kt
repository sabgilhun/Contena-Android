package com.sabgil.contena.data.mapper

import com.sabgil.contena.data.local.entities.NewProductEntity
import com.sabgil.contena.data.remote.dto.GetNewItemListResponse
import com.sabgil.contena.domain.model.NewProduct

interface NewProductMapper {

    fun toNewProducts(from: GetNewItemListResponse): List<NewProduct>

    fun toNewProduct(from: NewProductEntity): NewProduct
}