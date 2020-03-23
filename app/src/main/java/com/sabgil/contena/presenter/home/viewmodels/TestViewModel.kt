package com.sabgil.contena.presenter.home.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class TestViewModel @Inject constructor() : ViewModel() {
    fun test() {
        Log.i("test", "test")
    }
}
