package com.sabgil.contena.data.mapper

import com.sabgil.contena.data.remote.contena.dto.GetPostListResponse
import com.sabgil.contena.domain.model.Post

interface PostMapper {

    fun toPairOfCursorAndPostList(from: GetPostListResponse): Pair<Long, List<Post>>
}