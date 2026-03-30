package com.luvsoft.base.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import com.luvsoft.base.utils.ConnectionQuality
import com.luvsoft.base.utils.NetworkState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

class NetworkMonitor(
    context: Context,
    private val scope: CoroutineScope
) {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val _state = MutableStateFlow<NetworkState>(NetworkState.Offline)
    val state: StateFlow<NetworkState> = _state.asStateFlow()

    private val callback = object : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            scope.launch {
                checkQuality()
            }
        }

        override fun onLost(network: Network) {
            _state.value = NetworkState.Offline
        }

        override fun onUnavailable() {
            _state.value = NetworkState.Offline
        }
    }

    init {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                connectivityManager.registerDefaultNetworkCallback(callback)
            } else {
                val request = NetworkRequest.Builder()
                    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    .build()
                connectivityManager.registerNetworkCallback(request, callback)
            }
        } catch (e: Exception) {
            _state.value = NetworkState.Offline
        }
    }

    private suspend fun checkQuality() {
        val quality = withContext(Dispatchers.IO) {
            // 1. Detectar tipo de red actual
            val activeNetwork = connectivityManager.activeNetwork
            val caps = connectivityManager.getNetworkCapabilities(activeNetwork)
            val isCellular = caps?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true
            val isWifi = caps?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true

            // 2. Ajustar umbral según tipo de red
            //    - En WiFi somos más estrictos
            //    - En datos móviles somos MUCHO más relajados
            val slowThreshold: Long = when {
                isWifi -> 1000L   // 1s: arriba de esto lo consideramos lento en WiFi
                isCellular -> 2500L // 2.5s: damos más chance a datos móviles
                else -> 2000L
            }

            // ⚠️ Cambia esta URL por tu /ping o /health super ligero
            val testUrl = "https://tester.changarrito.mx/gestion-ventas"

            try {
                val url = URL(testUrl)
                val connection = (url.openConnection() as HttpURLConnection).apply {
                    connectTimeout = 5000  // 5s
                    readTimeout = 5000     // 5s
                    requestMethod = "HEAD"
                }

                val start = System.currentTimeMillis()
                connection.connect()
                val elapsed = System.currentTimeMillis() - start
                connection.disconnect()

                // 3. Clasificación simple
                when {
                    // Si respondió dentro del umbral → GOOD
                    elapsed <= slowThreshold -> ConnectionQuality.GOOD

                    // Solo marcamos SLOW si respondió pero tardó más del umbral
                    else -> ConnectionQuality.SLOW
                }
            } catch (e: Exception) {
                // Si ni siquiera logramos conectar (timeout, etc.) → sin calidad
                null
            }
        }

        _state.value = if (quality == null) {
            NetworkState.Offline
        } else {
            NetworkState.Online(quality)
        }
    }

}
