package com.example.itunessearch.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.itunessearch.backend.ITunesApi
import com.example.itunessearch.items.Discography
import com.example.itunessearch.utils.NetworkUtils
import kotlinx.coroutines.*

class SearchAlbumViewModel(application: Application) : AndroidViewModel(application) {

    private val job = Job()
    private val coroutine = CoroutineScope(Dispatchers.IO + job)
    private var discography = Discography()
    private val connectionStatus: MutableLiveData<Boolean> = MutableLiveData()
    private val searchResult : MutableLiveData<Discography> = MutableLiveData()

    private var searchRequest  = ""
    private var lastSearchRequest: String  = ""

    fun setSearchDiscography(searchRequest: String) {
        coroutine.launch {
            lastSearchRequest = searchRequest
            discography =  ITunesApi().getDiscography(searchRequest)
            searchResult.postValue(discography)
        }
    }

    fun checkNetwork() {
        coroutine.launch {
            val haveConnection = NetworkUtils(getApplication()).checkConnection()
            connectionStatus.postValue(haveConnection)
        }
    }

    fun getNetworkStatus() = connectionStatus
    fun getSearchResult() = searchResult

    fun setSearchRequest(searchRequest: String) {
        this.searchRequest = searchRequest
    }

    fun getSearchRequest() = searchRequest

    fun checkRepeatRequest(): Boolean {
        return lastSearchRequest.trim() == searchRequest.trim()
    }

    fun getAlbumItem(positionInDiscography: Int) = discography.albumList[positionInDiscography]

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
