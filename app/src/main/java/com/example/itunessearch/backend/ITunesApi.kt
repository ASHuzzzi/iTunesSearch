package com.example.itunessearch.backend

import com.example.itunessearch.items.Discography
import com.example.itunessearch.utils.Utils
import kotlinx.coroutines.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

private const val album = "album"
private const val baseUrl = "https://itunes.apple.com/"

class ITunesApi {

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .build()

    private val iTunesService: ITunesService by lazy {
        retrofit.create(ITunesService::class.java)
    }

    suspend fun getDiscography(author: String, resultSize: Int): Discography {
        var discography = Discography()
        withContext(Dispatchers.IO) {
            val searchResponse =  iTunesService.getDiscography(author, resultSize, album).await()
            discography = Utils().convertSearchResponseToDiscography(searchResponse)
        }
        return discography
    }
}