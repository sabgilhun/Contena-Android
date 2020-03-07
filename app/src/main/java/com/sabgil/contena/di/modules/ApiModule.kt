package com.sabgil.contena.di.modules

import com.sabgil.contena.di.annotations.ApplicationScope
import dagger.Module
import dagger.Provides
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {

    @Provides
    @ApplicationScope
    fun provideCallAdapterFactory(): CallAdapter.Factory = RxJava2CallAdapterFactory.create()

    @Provides
    @ApplicationScope
    fun provideConverterFactory(): Converter.Factory = GsonConverterFactory.create()
}