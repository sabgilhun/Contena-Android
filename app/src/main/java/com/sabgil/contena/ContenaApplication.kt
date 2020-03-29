package com.sabgil.contena

import android.app.Application
import com.sabgil.contena.di.component.ApplicationComponent
import com.sabgil.contena.di.component.DaggerApplicationComponent

class ContenaApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent
        private set

    override fun onCreate() {
        applicationComponent = DaggerApplicationComponent.factory().create(this)
        super.onCreate()
    }
}