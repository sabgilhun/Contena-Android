package com.sabgil.contena.presenter.home.enums

import com.sabgil.contena.presenter.home.model.SearchedShop

sealed class SearchingState {

    class NotStarted(val recommendedShopList: List<SearchedShop>) : SearchingState()
    class Searching(val keyword: String) : SearchingState()
    class Empty(val keyword: String) : SearchingState()
    class Complete(val searchedShops: List<SearchedShop>) : SearchingState()
}