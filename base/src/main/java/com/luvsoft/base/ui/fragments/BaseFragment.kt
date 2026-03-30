package com.luvsoft.base.ui.fragments

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.luvsoft.base.network.NetworkMonitorProvider
import com.luvsoft.base.utils.ConnectionQuality
import com.luvsoft.base.utils.NetUtil
import com.luvsoft.base.utils.NetworkState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

open class BaseFragment : Fragment() {

    private var snackbar: Snackbar? = null

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        verifyInternetStatus()

    }

    fun verifyInternetStatus(): Boolean {
        val monitor = NetworkMonitorProvider.monitor
        var isNotGood = false

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            monitor.state.collectLatest { state ->
                when (state) {
                    is NetworkState.Offline -> {
                        isNotGood = false
                        showBanner("Sin conexión. Algunas funciones podrían no funcionar.")
                        delay(1000)
                        hideBanner()
                    }

                    is NetworkState.Online -> {
                        when (state.quality) {
                            ConnectionQuality.GOOD -> {
                                hideBanner()
                            }
                            ConnectionQuality.SLOW -> {
                                isNotGood = true
                                showBanner(
                                    "Tu conexión está muy lenta. No eres tú, es tu internet que hoy anda con flojera 😅"
                                )
                                delay(1000)
                                hideBanner()
                            }
                        }
                    }
                }
            }
        }
        return isNotGood;
    }

    private fun showBanner(message: String) {
        val rootView = requireActivity().findViewById<View>(android.R.id.content) ?: return

        if (snackbar?.isShown == true) {
            snackbar?.setText(message)
        } else {
            snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_INDEFINITE)
            snackbar?.show()
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
     fun evaluateConnection(): Boolean {
        return !NetUtil.isOnline(requireContext()) && !verifyInternetStatus()
    }

    private fun hideBanner() {
        snackbar?.dismiss()
        snackbar = null
    }
}
