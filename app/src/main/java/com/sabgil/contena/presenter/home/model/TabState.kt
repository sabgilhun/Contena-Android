package com.sabgil.contena.presenter.home.model

enum class TabState(
    val listVisibility: Boolean,
    val loadingVisibility: Boolean,
    val refreshPageVisibility: Boolean
) {
    LOADING_FIRST_PAGE(
        listVisibility = false,
        loadingVisibility = true,
        refreshPageVisibility = false
    ),
    FAILED_FIRST_PAGE(
        listVisibility = false,
        loadingVisibility = false,
        refreshPageVisibility = true
    ),
    RELOAD_FIRST_PAGE(
        listVisibility = false,
        loadingVisibility = true,
        refreshPageVisibility = true
    ),
    SUCCESS_FIRST_PAGE(
        listVisibility = true,
        loadingVisibility = false,
        refreshPageVisibility = false
    )
}