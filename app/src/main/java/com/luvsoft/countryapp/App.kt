package com.luvsoft.countryapp

import android.app.Application
import com.luvsoft.base.network.NetworkMonitorProvider
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@HiltAndroidApp
class App : Application() {
    private val appScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    override fun onCreate() {
        super.onCreate()
        NetworkMonitorProvider.init(this, appScope)
    }
}