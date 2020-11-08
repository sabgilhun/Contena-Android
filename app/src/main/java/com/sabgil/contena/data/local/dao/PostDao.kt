package com.sabgil.contena.data.local.dao

import androidx.room.*
import com.sabgil.contena.data.local.entities.PostEntity
import io.reactivex.Single

@Dao
abstract class PostDao {

    @Query("SELECT * FROM POST")
    abstract fun selectAllPost(): Single<List<PostEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertPost(postEntity: PostEntity)

    @Delete
    abstract fun deletePost(postEntity: PostEntity)
}