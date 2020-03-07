package com.sabgil.contena.ui.base.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sabgil.contena.ContenaApplication

abstract class InjectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as ContenaApplication)
            .applicationComponent
            .activityComponent
            .inject(this)

        super.onCreate(savedInstanceState)
    }
}