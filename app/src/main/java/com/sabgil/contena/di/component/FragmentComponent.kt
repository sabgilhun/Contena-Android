package com.sabgil.contena.di.component

import com.sabgil.contena.di.annotation.FragmentScope
import com.sabgil.contena.presenter.base.InjectFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface FragmentComponent {

    fun inject(injectFragment: InjectFragment)
}