package com.sabgil.contena.common.ext

import androidx.lifecycle.MutableLiveData

val <T> MutableLiveData<List<T>>.valueOrEmpty
    get() = value?.toMutableList().orEmpty()