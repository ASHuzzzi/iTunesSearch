package com.example.itunessearch.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.itunessearch.items.AlbumItem

class AlbumDetailViewModel(application: Application): AndroidViewModel(application) {

    private var albumItem = AlbumItem()

    fun getAlbumItem() = albumItem

    fun setAlbumItem(albumItem: AlbumItem) {
        this.albumItem = albumItem
    }
}