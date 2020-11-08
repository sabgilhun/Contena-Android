package com.sabgil.contena.data.local.repository

import com.sabgil.contena.data.local.dao.NewProductDao
import com.sabgil.contena.data.local.dao.PostDao
import com.sabgil.contena.data.local.entities.NewProductEntity
import com.sabgil.contena.data.local.entities.PostEntity
import com.sabgil.contena.data.mapper.NewProductMapper
import com.sabgil.contena.data.mapper.PostMapper
import com.sabgil.contena.domain.model.NewProduct
import com.sabgil.contena.domain.model.Post
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val newProductDao: NewProductDao,
    private val postDao: PostDao,
    private val newProductMapper: NewProductMapper,
    private val postMapper: PostMapper
) : BookmarkRepository {

    override fun insertNewProduct(newProduct: NewProduct) =
        newProductDao.insertNewProduct(NewProductEntity.from(newProduct))

    override fun insertPost(post: Post) =
        postDao.insertPost(PostEntity.from(post))

    override fun deleteNewProduct(newProduct: NewProduct) =
        newProductDao.deleteNewProduct(NewProductEntity.from(newProduct))

    override fun deletePost(post: Post) =
        postDao.deletePost(PostEntity.from(post))

    override fun findBookmarkedNewProducts(): Single<List<NewProduct>> =
        newProductDao.selectAllNewProducts()
            .map { list -> list.map { newProductMapper.toNewProduct(it) } }

    override fun findBookmarkedPosts(): Single<List<Post>> =
        postDao.selectAllPost()
            .map { list -> list.map { postMapper.toPost(it) } }
}