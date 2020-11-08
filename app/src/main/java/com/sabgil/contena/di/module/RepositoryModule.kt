package com.sabgil.contena.di.module

import com.sabgil.contena.data.local.repository.AppRepository
import com.sabgil.contena.data.local.repository.AppRepositoryImpl
import com.sabgil.contena.data.local.repository.BookmarkRepository
import com.sabgil.contena.data.local.repository.BookmarkRepositoryImpl
import com.sabgil.contena.data.remote.repository.*
import com.sabgil.contena.di.annotation.ApplicationScope
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    @ApplicationScope
    abstract fun bindNewItemRepository(newItemRepositoryImpl: NewItemRepositoryImpl): NewItemRepository

    @Binds
    @ApplicationScope
    abstract fun bindPostRepository(postRepositoryImpl: PostRepositoryImpl): PostRepository

    @Binds
    @ApplicationScope
    abstract fun bindReportRepository(reportRepositoryImpl: ReportRepositoryImpl): ReportRepository

    @Binds
    @ApplicationScope
    abstract fun bindShopRepository(shopRepositoryImpl: ShopRepositoryImpl): ShopRepository

    @Binds
    @ApplicationScope
    abstract fun bindSubscriptionRepository(subscriptionRepositoryImpl: SubscriptionRepositoryImpl): SubscriptionRepository

    @Binds
    @ApplicationScope
    abstract fun bindAppRepository(appRepositoryImpl: AppRepositoryImpl): AppRepository

    @Binds
    @ApplicationScope
    abstract fun bindBookmarkRepository(bookmarkRepository: BookmarkRepositoryImpl): BookmarkRepository
}