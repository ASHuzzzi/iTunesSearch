package com.example.itunessearch.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.itunessearch.backend.ITunesApi
import com.example.itunessearch.items.Discography
import com.example.itunessearch.utils.NetworkUtils
import kotlinx.coroutines.*

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val job = Job()
    private val coroutine = CoroutineScope(Dispatchers.IO + job)
    private var discography = Discography()
    private val liveDataConnection: MutableLiveData<Boolean> = MutableLiveData()
    private val liveDataSearch : MutableLiveData<Discography> = MutableLiveData()

    fun setSearchDiscography(author: String, resultSize: Int) {
        coroutine.launch {
            Log.i("iTunesSearch", "Корутина пошла искать")
            discography =  ITunesApi().getDiscography(author, resultSize)
            liveDataSearch.postValue(discography)
        }
    }

    fun checkNetwork() {
        coroutine.launch {
            Log.i("iTunesSearch", "Корутина начала проверять сеть")
            val isConnected = NetworkUtils(getApplication()).checkConnection()
            liveDataConnection.postValue(isConnected)
        }
    }

    fun getNetworkStatus() = liveDataConnection
    fun getSearchResult() = liveDataSearch
    fun getDiscography() = discography

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
