package com.sabgil.contena.di.components

import com.sabgil.contena.di.annotations.ActivityScope
import com.sabgil.contena.di.modules.ViewModelModule
import com.sabgil.contena.presenter.base.InjectActivity
import dagger.Subcomponent


@ActivityScope
@Subcomponent
interface ActivityComponent {

    val fragmentComponent: FragmentComponent

    fun inject(injectActivity: InjectActivity)
}