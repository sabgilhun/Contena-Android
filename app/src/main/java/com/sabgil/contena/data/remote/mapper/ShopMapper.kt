package com.sabgil.contena.data.remote.mapper

import com.sabgil.contena.data.remote.dto.GetAllShopListResponse
import com.sabgil.contena.data.remote.dto.GetAvailableShopListResponse
import com.sabgil.contena.data.remote.dto.GetRecommendShopListResponse
import com.sabgil.contena.data.remote.dto.GetSubscriptionShopListResponse
import com.sabgil.contena.domain.model.Shop


interface ShopMapper {

    fun toShopList(from: GetAllShopListResponse): List<Shop>

    fun toShopList(from: GetRecommendShopListResponse): List<Shop>

    fun toShopList(from: GetAvailableShopListResponse): List<Shop>

    fun toShopList(from: GetSubscriptionShopListResponse): List<Shop>
}