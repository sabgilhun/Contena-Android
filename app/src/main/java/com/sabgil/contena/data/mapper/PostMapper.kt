package com.sabgil.contena.data.mapper

import com.sabgil.contena.data.local.entities.PostEntity
import com.sabgil.contena.data.remote.dto.GetPostListResponse
import com.sabgil.contena.domain.model.Post

interface PostMapper {

    fun toPairOfCursorAndPostList(from: GetPostListResponse): Pair<Long, List<Post>>

    fun toPost(from: PostEntity): Post
}