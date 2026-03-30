package com.luvsoft.base.utils

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WalletPrefs @Inject constructor(
    @ApplicationContext context: Context
) {

    private val prefs = context.getSharedPreferences("wallet_prefs", Context.MODE_PRIVATE)

    fun saveWalletId(id: Int) {
        prefs.edit()
            .putInt("WALLET_ID", id)
            .apply()
    }

    fun getWalletId(): Int {
        return prefs.getInt("WALLET_ID", -1)
    }
}