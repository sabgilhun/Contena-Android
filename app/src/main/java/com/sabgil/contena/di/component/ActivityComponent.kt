package com.sabgil.contena.di.component

import com.sabgil.contena.di.annotation.ActivityScope
import com.sabgil.contena.presenter.base.InjectActivity
import dagger.Subcomponent


@ActivityScope
@Subcomponent
interface ActivityComponent {

    val fragmentComponent: FragmentComponent

    fun inject(injectActivity: InjectActivity)
}