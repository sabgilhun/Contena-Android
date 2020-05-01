package com.sabgil.contena.data.repository

import com.sabgil.contena.data.local.AppSharedPreference
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val appSharedPreference: AppSharedPreference
) : AppRepository {

    override fun putFcmToken(token: String) {
        appSharedPreference.saveToken(token)
    }

    override fun getFcmToken(): String? = appSharedPreference.getToken()
}