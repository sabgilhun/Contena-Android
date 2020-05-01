package com.sabgil.contena.di.module

import com.sabgil.contena.data.repository.AppRepository
import com.sabgil.contena.data.repository.AppRepositoryImpl
import com.sabgil.contena.data.repository.ContenaRepository
import com.sabgil.contena.data.repository.ContenaRepositoryImpl
import com.sabgil.contena.di.annotation.ApplicationScope
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    @ApplicationScope
    abstract fun bindContenaRepository(contenaRepositoryImpl: ContenaRepositoryImpl): ContenaRepository

    @Binds
    @ApplicationScope
    abstract fun bindAppRepository(appRepositoryImpl: AppRepositoryImpl): AppRepository
}