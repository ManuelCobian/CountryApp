package com.luvsoft.base.network

import android.content.Context
import kotlinx.coroutines.CoroutineScope

object NetworkMonitorProvider {
    @Volatile
    private var _monitor: NetworkMonitor? = null

    val monitor: NetworkMonitor
        get() = _monitor
            ?: throw IllegalStateException("NetworkMonitorProvider no ha sido inicializado. Llama init() en Application")

    fun init(context: Context, scope: CoroutineScope) {
        if (_monitor == null) {
            synchronized(this) {
                if (_monitor == null) {
                    _monitor = NetworkMonitor(context.applicationContext, scope)
                }
            }
        }
    }
}