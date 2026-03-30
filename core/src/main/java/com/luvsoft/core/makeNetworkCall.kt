package com.luvsoft.core

import android.util.Log
import com.luvsoft.base.R

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

suspend fun <T> makeNetworkCall(
    call: suspend () -> T
): ApiResponseStatus<T> {
    return withContext(Dispatchers.IO) {
        try {
            ApiResponseStatus.Success(call())
        } catch (e: UnknownHostException) {
            ApiResponseStatus.Error(R.string.unknown_host_exception_error)
        } catch (e: Exception) {

            Log.d("error",e.message.toString())
            ApiResponseStatus.Error(R.string.unknown_exception_error)
        }
    }
}