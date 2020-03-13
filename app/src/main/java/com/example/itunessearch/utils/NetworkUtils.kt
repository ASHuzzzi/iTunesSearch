package com.example.itunessearch.utils

import android.content.Context
import android.net.ConnectivityManager
import java.io.IOException

class NetworkUtils(private val context: Context) {

    fun checkConnection(): Boolean {
        val connectivityManager =
            context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.let { connectivityManager.activeNetworkInfo } ?: null
        if (activeNetwork != null && activeNetwork.isConnected) {
            val runtime = Runtime.getRuntime()
            try {
                val ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8")
                val exitValue = ipProcess.waitFor()
                return exitValue == 0
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            return true
        }
        return false
    }
}