package com.example.itunessearch.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.itunessearch.R
import com.example.itunessearch.items.AlbumItem
import com.example.itunessearch.items.Discography
import com.example.itunessearch.ui.fragments.SearchAlbumFragment

class SearchResultAdapter (private val fragment: SearchAlbumFragment):
    RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {

    var discography = Discography()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder internal constructor(itemView: View): RecyclerView.ViewHolder(itemView)  {

        private val albumName = itemView.findViewById<TextView>(R.id.albumName)

        fun bind(albumItem: AlbumItem) {
            albumName.text = albumItem.collectionName
            itemView.setOnClickListener{
                fragment.openAlbumDetailFragment(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.album_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(discography.albumList[position])
    }

    override fun getItemCount(): Int = discography.albumList.size
}