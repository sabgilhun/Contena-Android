package com.sabgil.contena.common.pagemanager

data class PageHolder<T>(
    val items: List<T>,
    val cursor: Long
)