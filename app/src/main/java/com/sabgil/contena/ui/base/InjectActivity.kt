package com.sabgil.contena.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.sabgil.contena.ContenaApplication
import javax.inject.Inject

abstract class InjectActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as ContenaApplication)
            .applicationComponent
            .activityComponent
            .inject(this)

        super.onCreate(savedInstanceState)
    }
}