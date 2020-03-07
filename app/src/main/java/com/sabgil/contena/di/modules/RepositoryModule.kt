package com.sabgil.contena.di.modules

import com.sabgil.contena.data.repositories.ContenaRepository
import com.sabgil.contena.data.repositories.ContenaRepositoryImpl
import com.sabgil.contena.di.annotations.ApplicationScope
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    @ApplicationScope
    abstract fun bindContenaRepository(contenaRepositoryImpl: ContenaRepositoryImpl): ContenaRepository
}