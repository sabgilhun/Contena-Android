package com.sabgil.contena.commons

data class PageHolder<T>(
    val items: List<T>,
    val cursor: Long
)