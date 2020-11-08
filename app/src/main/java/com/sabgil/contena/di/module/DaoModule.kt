package com.sabgil.contena.di.module

import com.sabgil.contena.data.local.dao.NewProductDao
import com.sabgil.contena.data.local.dao.PostDao
import com.sabgil.contena.data.local.db.BookmarkDatabase
import com.sabgil.contena.di.annotation.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class DaoModule {

    @Provides
    @ApplicationScope
    fun provideNewProductDao(
        bookmarkDatabase: BookmarkDatabase
    ): NewProductDao = bookmarkDatabase.getNewProductDao()

    @Provides
    @ApplicationScope
    fun providePostDao(
        bookmarkDatabase: BookmarkDatabase
    ): PostDao = bookmarkDatabase.getPostDao()
}