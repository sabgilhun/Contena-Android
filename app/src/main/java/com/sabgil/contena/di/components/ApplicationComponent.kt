package com.sabgil.contena.di.components

import android.content.Context
import com.sabgil.contena.di.annotations.ApplicationScope
import com.sabgil.contena.di.modules.ApiModule
import com.sabgil.contena.di.modules.NetworkModule
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        ApiModule::class,
        NetworkModule::class
    ]
)
interface ApplicationComponent {

    val activityComponent: ActivityComponent

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }
}