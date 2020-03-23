package com.sabgil.contena.di.components

import com.sabgil.contena.di.annotations.FragmentScope
import com.sabgil.contena.presenter.base.InjectFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface FragmentComponent {

    fun inject(injectFragment: InjectFragment)
}