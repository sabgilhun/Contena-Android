package com.sabgil.contena.di.module

import com.sabgil.contena.data.remote.api.*
import com.sabgil.contena.di.annotation.ApplicationScope
import dagger.Module
import dagger.Provides
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {

    @Provides
    @ApplicationScope
    fun provideNewItemApi(
        retrofit: Retrofit
    ): NewItemApi = retrofit.create(NewItemApi::class.java)

    @Provides
    @ApplicationScope
    fun providePostApi(
        retrofit: Retrofit
    ): PostApi = retrofit.create(PostApi::class.java)

    @Provides
    @ApplicationScope
    fun provideReportApi(
        retrofit: Retrofit
    ): ReportApi = retrofit.create(ReportApi::class.java)

    @Provides
    @ApplicationScope
    fun provideShopApi(
        retrofit: Retrofit
    ): ShopApi = retrofit.create(ShopApi::class.java)

    @Provides
    @ApplicationScope
    fun provideSubscriptionApi(
        retrofit: Retrofit
    ): SubscriptionApi = retrofit.create(SubscriptionApi::class.java)

    @Provides
    @ApplicationScope
    fun provideCallAdapterFactory(): CallAdapter.Factory = RxJava2CallAdapterFactory.create()

    @Provides
    @ApplicationScope
    fun provideConverterFactory(): Converter.Factory = GsonConverterFactory.create()
}