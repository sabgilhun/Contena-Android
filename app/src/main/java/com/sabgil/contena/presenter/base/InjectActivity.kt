package com.sabgil.contena.presenter.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.sabgil.contena.ContenaApplication
import com.sabgil.contena.di.component.ActivityComponent
import javax.inject.Inject

abstract class InjectActivity : AppCompatActivity() {

    lateinit var activityComponent: ActivityComponent
        private set

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        activityComponent = (application as ContenaApplication)
            .applicationComponent
            .activityComponent

        activityComponent.inject(this)

        super.onCreate(savedInstanceState)
    }
}