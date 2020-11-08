package com.sabgil.contena.di.module

import android.content.Context
import com.sabgil.contena.data.local.pref.AppSharedPreference
import com.sabgil.contena.di.annotation.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class SharedPrefModule {

    @Provides
    @ApplicationScope
    fun provideAppShredPref(
        context: Context
    ) = AppSharedPreference(context)
}