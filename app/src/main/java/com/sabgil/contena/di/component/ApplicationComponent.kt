package com.sabgil.contena.di.component

import android.content.Context
import com.sabgil.contena.di.annotation.ApplicationScope
import com.sabgil.contena.di.module.*
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        ApiModule::class,
        DaoModule::class,
        DbModule::class,
        MapperModule::class,
        SharedPrefModule::class,
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