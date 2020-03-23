package com.sabgil.contena.presenter.base

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sabgil.contena.di.components.FragmentComponent
import javax.inject.Inject
import javax.inject.Named

abstract class InjectFragment : Fragment() {

    lateinit var fragmentComponent: FragmentComponent
        private set

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onAttach(context: Context) {
        fragmentComponent = (context as InjectActivity)
            .activityComponent
            .fragmentComponent

        fragmentComponent.inject(this)

        super.onAttach(context as Context)
    }
}