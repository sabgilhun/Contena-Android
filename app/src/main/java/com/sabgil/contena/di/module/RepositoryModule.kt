package com.sabgil.contena.di.module

import com.sabgil.contena.data.repository.*
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
}