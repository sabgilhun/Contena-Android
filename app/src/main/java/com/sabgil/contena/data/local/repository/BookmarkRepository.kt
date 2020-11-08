package com.sabgil.contena.data.local.repository

import com.sabgil.contena.domain.model.NewProduct
import com.sabgil.contena.domain.model.Post
import io.reactivex.Completable
import io.reactivex.Single

interface BookmarkRepository {

    fun insertNewProduct(newProduct: NewProduct): Completable

    fun insertPost(post: Post): Completable

    fun deleteNewProduct(newProduct: NewProduct): Completable

    fun deletePost(post: Post): Completable

    fun findBookmarkedNewProducts(): Single<List<NewProduct>>

    fun findBookmarkedPosts(): Single<List<Post>>
}