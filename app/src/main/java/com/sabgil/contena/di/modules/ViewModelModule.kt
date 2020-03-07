package com.sabgil.contena.di.modules

import androidx.lifecycle.ViewModelProvider
import com.sabgil.contena.di.annotations.ActivityScope
import com.sabgil.contena.di.factories.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {

    @ActivityScope
    @Binds
    abstract fun bindFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}