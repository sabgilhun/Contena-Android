package com.sabgil.contena.data.mapper

import com.sabgil.contena.data.remote.contena.dto.GetNewItemListResponse
import com.sabgil.contena.domain.model.NewItem

interface NewItemMapper {

    fun toNewItemList(from: GetNewItemListResponse): List<NewItem>
}