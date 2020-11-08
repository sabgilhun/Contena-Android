package com.sabgil.contena.data.local.dao

import androidx.room.*
import com.sabgil.contena.data.local.entities.NewProductEntity
import io.reactivex.Completable
import io.reactivex.Single


@Dao
abstract class NewProductDao {

    @Query("SELECT * FROM NEW_PRODUCT")
    abstract fun selectAllNewProducts(): Single<List<NewProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertNewProduct(newProductEntity: NewProductEntity): Completable

    @Delete
    abstract fun deleteNewProduct(newProductEntity: NewProductEntity): Completable
}