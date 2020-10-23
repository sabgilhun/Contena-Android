package com.sabgil.contena.data.mapper

import com.sabgil.contena.data.remote.contena.dto.GetAllShopListResponse
import com.sabgil.contena.data.remote.contena.dto.GetAvailableShopListResponse
import com.sabgil.contena.data.remote.contena.dto.GetRecommendShopListResponse
import com.sabgil.contena.data.remote.contena.dto.GetSubscriptionShopListResponse
import com.sabgil.contena.domain.model.Shop


interface ShopMapper {

    fun toShopList(from: GetAllShopListResponse): List<Shop>

    fun toShopList(from: GetRecommendShopListResponse): List<Shop>

    fun toShopList(from: GetAvailableShopListResponse): List<Shop>

    fun toShopList(from: GetSubscriptionShopListResponse): List<Shop>
}