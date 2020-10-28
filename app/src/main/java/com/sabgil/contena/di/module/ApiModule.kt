package com.sabgil.contena.di.module

import com.sabgil.contena.CONTENA_BASE_URL
import com.sabgil.contena.data.remote.contena.api.*
import com.sabgil.contena.di.annotation.ApplicationScope
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
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
        okHttpClient: OkHttpClient,
        callAdapterFactory: CallAdapter.Factory,
        converterFactory: Converter.Factory
    ): NewItemApi = Retrofit.Builder()
        .baseUrl(CONTENA_BASE_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(callAdapterFactory)
        .addConverterFactory(converterFactory)
        .build()
        .create(NewItemApi::class.java)

    @Provides
    @ApplicationScope
    fun providePostApi(
        okHttpClient: OkHttpClient,
        callAdapterFactory: CallAdapter.Factory,
        converterFactory: Converter.Factory
    ): PostApi = Retrofit.Builder()
        .baseUrl(CONTENA_BASE_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(callAdapterFactory)
        .addConverterFactory(converterFactory)
        .build()
        .create(PostApi::class.java)

    @Provides
    @ApplicationScope
    fun provideReportApi(
        okHttpClient: OkHttpClient,
        callAdapterFactory: CallAdapter.Factory,
        converterFactory: Converter.Factory
    ): ReportApi = Retrofit.Builder()
        .baseUrl(CONTENA_BASE_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(callAdapterFactory)
        .addConverterFactory(converterFactory)
        .build()
        .create(ReportApi::class.java)

    @Provides
    @ApplicationScope
    fun provideShopApi(
        okHttpClient: OkHttpClient,
        callAdapterFactory: CallAdapter.Factory,
        converterFactory: Converter.Factory
    ): ShopApi = Retrofit.Builder()
        .baseUrl(CONTENA_BASE_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(callAdapterFactory)
        .addConverterFactory(converterFactory)
        .build()
        .create(ShopApi::class.java)

    @Provides
    @ApplicationScope
    fun provideSubscriptionApi(
        okHttpClient: OkHttpClient,
        callAdapterFactory: CallAdapter.Factory,
        converterFactory: Converter.Factory
    ): SubscriptionApi = Retrofit.Builder()
        .baseUrl(CONTENA_BASE_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(callAdapterFactory)
        .addConverterFactory(converterFactory)
        .build()
        .create(SubscriptionApi::class.java)

    @Provides
    @ApplicationScope
    fun provideCallAdapterFactory(): CallAdapter.Factory = RxJava2CallAdapterFactory.create()

    @Provides
    @ApplicationScope
    fun provideConverterFactory(): Converter.Factory = GsonConverterFactory.create()
}