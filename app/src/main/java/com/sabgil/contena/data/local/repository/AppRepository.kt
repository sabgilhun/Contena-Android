package com.sabgil.contena.data.local.repository

interface AppRepository {

    fun putFcmToken(token: String)

    fun getFcmToken(): String?
}
