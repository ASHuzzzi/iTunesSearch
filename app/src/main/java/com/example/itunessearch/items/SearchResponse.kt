package com.example.itunessearch.items

import com.google.gson.annotations.SerializedName

class SearchResponse(@SerializedName("resultCount")
                     val resultCount: Int = 0,
                     @SerializedName("results")
                     val resultList: MutableList<SearchItem> = mutableListOf())