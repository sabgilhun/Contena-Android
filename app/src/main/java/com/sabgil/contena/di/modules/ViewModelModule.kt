package com.sabgil.contena.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sabgil.contena.di.annotations.ApplicationScope
import com.sabgil.contena.di.annotations.ViewModelKey
import com.sabgil.contena.di.factories.ViewModelFactory
import com.sabgil.contena.presenter.home.viewmodels.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @ApplicationScope
    @Binds
    abstract fun bindFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel
}