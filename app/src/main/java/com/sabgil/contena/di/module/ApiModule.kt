package com.sabgil.contena.di.module

import com.sabgil.contena.CONTENA_BASE_URL
import com.sabgil.contena.data.remote.contena.ContenaApi
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
    fun provideContenaApi(
        okHttpClient: OkHttpClient,
        callAdapterFactory: CallAdapter.Factory,
        converterFactory: Converter.Factory
    ): ContenaApi = Retrofit.Builder()
        .baseUrl(CONTENA_BASE_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(callAdapterFactory)
        .addConverterFactory(converterFactory)
        .build()
        .create(ContenaApi::class.java)

    @Provides
    @ApplicationScope
    fun provideCallAdapterFactory(): CallAdapter.Factory = RxJava2CallAdapterFactory.create()

    @Provides
    @ApplicationScope
    fun provideConverterFactory(): Converter.Factory = GsonConverterFactory.create()
}