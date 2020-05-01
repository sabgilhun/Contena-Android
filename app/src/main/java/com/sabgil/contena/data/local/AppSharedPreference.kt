package com.sabgil.contena.data.local

import android.content.Context
import android.content.SharedPreferences
import com.sabgil.contena.BuildConfig

class AppSharedPreference(
    context: Context
) {
    private val pref: SharedPreferences = context.applicationContext
        .getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        pref.edit().putString(KEY_TOKEN, token).apply()
    }

    fun getToken() = pref.getString(KEY_TOKEN, null)

    companion object {
        private const val KEY_TOKEN = "KEY_TOKEN"
    }
}
