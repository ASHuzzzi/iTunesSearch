package com.example.itunessearch.utils

import com.example.itunessearch.items.AlbumItem
import com.example.itunessearch.items.Discography
import com.example.itunessearch.items.SearchResponse

class Utils {

    fun convertSearchResponseToDiscography(searchResponse: SearchResponse): Discography {
        val discography = Discography()
        searchResponse.resultList
            .toList()
            .sortedBy { it.collectionName }
            .forEach {
                val albumItem = AlbumItem(
                    it.artworkUrl,
                    it.artistName,
                    it.collectionName,
                    it.genre,
                    it.trackCount)
            discography.albumList.add(albumItem)
        }
        return discography
    }
}