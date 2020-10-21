package com.sabgil.contena.data.mapper

import com.sabgil.contena.data.remote.contena.dto.GetNewItemListResponse
import com.sabgil.contena.data.remote.contena.dto.GetPostListResponse
import com.sabgil.contena.domain.model.NewItem
import com.sabgil.contena.domain.model.Post
import javax.inject.Inject

class PostMapperImpl @Inject constructor() : PostMapper {

    override fun toPairOfCursorAndPostList(from: GetPostListResponse): Pair<Long, List<Post>> {
        TODO("Not yet implemented")
    }
}