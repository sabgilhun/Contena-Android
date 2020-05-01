package com.sabgil.contena.data.repository

interface AppRepository {

    fun putFcmToken(token: String)

    fun getFcmToken(): String?
}
