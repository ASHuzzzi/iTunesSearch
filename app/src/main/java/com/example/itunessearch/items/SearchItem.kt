package com.example.itunessearch.items

import com.google.gson.annotations.SerializedName

class SearchItem(@SerializedName("artworkUrl100")
                 val artworkUrl: String = "",
                 @SerializedName("artistName")
                 val artistName: String = "",
                 @SerializedName("collectionName")
                 val collectionName: String = "",
                 @SerializedName("primaryGenreName")
                 val genre: String = "",
                 @SerializedName("trackCount")
                 val trackCount: String = "")