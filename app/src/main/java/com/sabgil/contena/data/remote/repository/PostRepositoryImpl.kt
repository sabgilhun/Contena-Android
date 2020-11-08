package com.sabgil.contena.data.remote.repository

import com.sabgil.contena.data.mapper.PostMapper
import com.sabgil.contena.data.remote.api.PostApi
import com.sabgil.contena.domain.model.Post
import io.reactivex.Single
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val postApi: PostApi,
    private val postMapper: PostMapper
) : PostRepository {

    override fun getPostList(userId: String, cursor: Long?): Single<Pair<Long, List<Post>>> =
        postApi.getPostList(userId, cursor).map(postMapper::toPairOfCursorAndPostList)
}