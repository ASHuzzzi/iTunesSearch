package com.example.itunessearch.backend

import com.example.itunessearch.items.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private const val parameterEntity = "entity"
private const val parameterLimit = "limit"
private const val parameterTerm = "term"
private const val urlSuffixSearch = "search?"

interface ITunesService {

    @GET(urlSuffixSearch)
    fun getDiscography(
        @Query(parameterTerm) term: String,
        @Query(parameterLimit) limit: Int,
        @Query(parameterEntity) entity: String
    ): Call<SearchResponse>
}