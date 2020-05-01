package com.sabgil.contena.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sabgil.contena.di.annotation.ApplicationScope
import com.sabgil.contena.di.annotation.ViewModelKey
import com.sabgil.contena.di.factory.ViewModelFactory
import com.sabgil.contena.presenter.home.viewmodel.HomeViewModel
import com.sabgil.contena.presenter.home.viewmodel.NewItemTabViewModel
import com.sabgil.contena.presenter.home.viewmodel.SearchTabViewModel
import com.sabgil.contena.presenter.manage.viewmodel.ShopManageViewModel
import com.sabgil.contena.presenter.postdetail.viewmodel.PostDetailViewModel
import com.sabgil.contena.presenter.settings.viewmodel.SettingsViewModel
import com.sabgil.contena.presenter.splash.SplashViewModel
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
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(splashViewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NewItemTabViewModel::class)
    abstract fun bindNewItemTabViewModel(newItemTabViewModel: NewItemTabViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchTabViewModel::class)
    abstract fun bindSearchTabViewModel(searchTabViewModel: SearchTabViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PostDetailViewModel::class)
    abstract fun bindPostDetailViewModel(postDetailViewModel: PostDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun bindSettingsViewModel(settingsViewModel: SettingsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ShopManageViewModel::class)
    abstract fun bindShopManageViewModel(shopManageViewModel: ShopManageViewModel): ViewModel
}