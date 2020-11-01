package com.sabgil.contena.presenter.home.viewmodel

import androidx.lifecycle.MutableLiveData
import com.sabgil.contena.presenter.base.BaseViewModel
import javax.inject.Inject

class HomeViewModel @Inject constructor() : BaseViewModel() {

    val needsPostReload = MutableLiveData<Boolean>()
}
