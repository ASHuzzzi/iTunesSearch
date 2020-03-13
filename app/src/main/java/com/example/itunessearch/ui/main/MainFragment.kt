package com.example.itunessearch.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.itunessearch.R
import kotlinx.android.synthetic.main.main_fragment.*
import kotlin.random.Random

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        buttonSearch.setOnClickListener {
            viewModel.checkNetwork()
        }

        viewModel.getNetworkStatus().observe(viewLifecycleOwner, Observer { isConnection ->
            if (isConnection ) {
                if (viewModel.getDiscography().albumList.size == 0 ) {
                    viewModel.setSearchDiscography(editTextSearch.text.toString(), 9)
                }
            } else {
                Toast.makeText(activity, "Нет сети", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.getSearchResult().observe(viewLifecycleOwner, Observer { discography ->
            discography.albumList
                .toList()
                .sortedBy { it.collectionName}
                .forEach { Log.i("Список альбомов", it.collectionName) }
            message.text = discography.albumList[Random.nextInt(0, discography.albumList.size)].collectionName
        })
    }
}

