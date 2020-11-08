package com.sabgil.contena.di.module

import com.sabgil.contena.data.mapper.*
import com.sabgil.contena.di.annotation.ApplicationScope
import dagger.Binds
import dagger.Module

@Module
abstract class MapperModule {

    @Binds
    @ApplicationScope
    abstract fun bindNewProductMapper(newProductMapperImpl: NewProductMapperImpl): NewProductMapper

    @Binds
    @ApplicationScope
    abstract fun bindPostMapper(postMapperImpl: PostMapperImpl): PostMapper

    @Binds
    @ApplicationScope
    abstract fun bindReportMapper(reportMapperImpl: ReportMapperImpl): ReportMapper

    @Binds
    @ApplicationScope
    abstract fun bindShopMapper(shopMapperImpl: ShopMapperImpl): ShopMapper

    @Binds
    @ApplicationScope
    abstract fun bindSubscriptionMapper(subscriptionMapperImpl: SubscriptionMapperImpl): SubscriptionMapper
}