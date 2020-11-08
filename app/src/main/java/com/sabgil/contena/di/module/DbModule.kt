package com.sabgil.contena.di.module

import android.content.Context
import androidx.room.Room
import com.sabgil.contena.data.local.db.BookmarkDatabase
import com.sabgil.contena.di.annotation.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class DbModule {

    @Provides
    @ApplicationScope
    fun provideBookmarkDb(
        context: Context
    ): BookmarkDatabase = Room.databaseBuilder(
        context.applicationContext,
        BookmarkDatabase::class.java,
        DB_NAME
    ).build()

    companion object {
        private const val DB_NAME = "bookmark.db"
    }
}