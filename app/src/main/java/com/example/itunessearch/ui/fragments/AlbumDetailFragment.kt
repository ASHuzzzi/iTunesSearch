package com.example.itunessearch.ui.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.util.DisplayMetrics
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
import kotlin.math.roundToInt

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

    override fun onStart() {
        super.onStart()
        val imageSideSize = getImageViewSize()
        imageArtwork.layoutParams.height = imageSideSize
        imageArtwork.layoutParams.width = imageSideSize
        Glide.with(this).load(viewModel.getAlbumItem().artworkUrl).into(imageArtwork)
    }

    override fun onResume() {
        super.onResume()
        albumTitle.text = viewModel.getAlbumItem().collectionName
        artistName.text = viewModel.getAlbumItem().artistName
        genre.text = viewModel.getAlbumItem().genre
        trackCount.text = viewModel.getAlbumItem().trackCount
    }

    private fun getImageViewSize(): Int {
        val ratioForView = 0.4
        val orientation = requireActivity().resources.configuration.orientation
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenSide =
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                displayMetrics.widthPixels
            } else {
                displayMetrics.heightPixels
            }
        return (screenSide * ratioForView).roundToInt()
    }
}
