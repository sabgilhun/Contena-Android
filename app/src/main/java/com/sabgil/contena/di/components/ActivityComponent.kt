package com.sabgil.contena.di.components

import com.sabgil.contena.di.annotations.ActivityScope
import com.sabgil.contena.di.modules.ViewModelModule
import com.sabgil.contena.ui.base.InjectActivity
import dagger.Subcomponent


@ActivityScope
@Subcomponent(modules = [ViewModelModule::class])
interface ActivityComponent {

    fun inject(injectActivity: InjectActivity)
}