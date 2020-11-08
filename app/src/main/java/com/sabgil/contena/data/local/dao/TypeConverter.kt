package com.sabgil.contena.data.local.dao

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sabgil.contena.domain.model.NewProduct
import java.lang.reflect.Type

class TypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromNewProductList(newProductList: List<NewProduct>): String =
        gson.toJson(newProductList)

    @TypeConverter
    fun toNewProductList(json: String): List<NewProduct> =
        gson.fromJson(json, newProductListTypeToken)

    companion object {
        private val newProductListTypeToken: Type = object : TypeToken<List<NewProduct>>() {}.type
    }
}