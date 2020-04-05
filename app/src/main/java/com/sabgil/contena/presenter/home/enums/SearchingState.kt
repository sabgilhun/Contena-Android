package com.sabgil.contena.presenter.home.enums

import com.sabgil.contena.domain.model.Shop

sealed class SearchingState {

    class NotStarted(val recommendedShopList: List<Shop>) : SearchingState()
    class Searching(val keyword: String) : SearchingState()
    class Empty(val keyword: String) : SearchingState()
    class Complete(val searchedShopList: List<Shop>) : SearchingState()
}