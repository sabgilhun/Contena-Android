package com.sabgil.contena.data.mapper

import com.sabgil.contena.data.remote.contena.dto.GetNewItemListResponse
import com.sabgil.contena.domain.model.NewItem
import javax.inject.Inject

class NewItemMapperImpl @Inject constructor() :NewItemMapper {

    override fun toNewItemList(from: GetNewItemListResponse): List<NewItem> {
        TODO("Not yet implemented")
    }
}