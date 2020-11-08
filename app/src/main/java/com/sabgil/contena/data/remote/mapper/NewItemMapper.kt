package com.sabgil.contena.data.remote.mapper

import com.sabgil.contena.data.remote.dto.GetNewItemListResponse
import com.sabgil.contena.domain.model.NewProduct

interface NewItemMapper {

    fun toNewItemList(from: GetNewItemListResponse): List<NewProduct>
}