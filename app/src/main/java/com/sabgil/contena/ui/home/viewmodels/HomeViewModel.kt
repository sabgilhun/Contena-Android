package com.sabgil.contena.ui.home.viewmodels

import com.sabgil.contena.data.repositories.ContenaRepository
import com.sabgil.contena.ui.base.BaseViewModel
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val contenaRepository: ContenaRepository
) : BaseViewModel() {
}