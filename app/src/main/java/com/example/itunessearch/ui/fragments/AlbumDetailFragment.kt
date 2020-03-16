package com.example.itunessearch.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.itunessearch.R
import com.example.itunessearch.items.AlbumItem
import com.example.itunessearch.ui.viewModel.AlbumDetailViewModel
import kotlinx.android.synthetic.main.album_detail_fragment.*

class AlbumDetailFragment: Fragment() {

    private lateinit var viewModel: AlbumDetailViewModel
    private val bundleTag = "AlbumItem"

    companion object {
        fun newInstance(albumItem: AlbumItem) = AlbumDetailFragment().apply {
            arguments = Bundle().apply {
                putSerializable(bundleTag, albumItem)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.album_detail_fragment, container, false)
        viewModel = ViewModelProvider(this).get(AlbumDetailViewModel::class.java)
        val bundle = this.arguments
        val albumItem = bundle?.getSerializable(bundleTag) as AlbumItem
        viewModel.setAlbumItem(albumItem)
        return view
    }

    override fun onResume() {
        super.onResume()
        albumTitle.text = viewModel.getAlbumItem().collectionName
        artistName.text = viewModel.getAlbumItem().artistName
        genre.text = viewModel.getAlbumItem().genre
        trackCount.text = viewModel.getAlbumItem().trackCount
        Glide.with(this).load(viewModel.getAlbumItem().artworkUrl).into(imageArtwork)
        Log.i(bundleTag, viewModel.getAlbumItem().collectionName)
    }
}
