package com.sabgil.contena.di.module

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
}