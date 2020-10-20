package com.sabgil.contena.data.repository

import com.sabgil.contena.domain.model.Post
import io.reactivex.Single

interface PostRepository {

    fun getPostList(userId: String, cursor: Long?): Single<Pair<Long, List<Post>>>
}