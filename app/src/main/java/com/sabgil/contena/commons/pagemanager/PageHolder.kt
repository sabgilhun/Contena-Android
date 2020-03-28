package com.sabgil.contena.commons.pagemanager

data class PageHolder<T>(
    val items: List<T>,
    val cursor: Long
)