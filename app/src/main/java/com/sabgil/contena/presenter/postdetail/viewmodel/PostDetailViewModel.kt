package com.sabgil.contena.presenter.postdetail.viewmodel

import com.sabgil.contena.data.repository.ContenaRepository
import com.sabgil.contena.presenter.base.BaseViewModel
import javax.inject.Inject

class PostDetailViewModel @Inject constructor(
    private val contenaRepository: ContenaRepository
) : BaseViewModel() {
}