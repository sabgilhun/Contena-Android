package com.sabgil.contena.di.component

import android.content.Context
import com.sabgil.contena.di.annotation.ApplicationScope
import com.sabgil.contena.di.module.ApiModule
import com.sabgil.contena.di.module.NetworkModule
import com.sabgil.contena.di.module.RepositoryModule
import com.sabgil.contena.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        ApiModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    val activityComponent: ActivityComponent

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }
}