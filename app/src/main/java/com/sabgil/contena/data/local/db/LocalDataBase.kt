package com.sabgil.contena.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sabgil.contena.data.local.dao.NewProductDao
import com.sabgil.contena.data.local.dao.PostDao
import com.sabgil.contena.data.local.dao.TypeConverter
import com.sabgil.contena.data.local.entities.NewProductEntity
import com.sabgil.contena.data.local.entities.PostEntity

@Database(
    entities = [NewProductEntity::class, PostEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(TypeConverter::class)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun getPostDao(): PostDao

    abstract fun getNewProductDao(): NewProductDao
}