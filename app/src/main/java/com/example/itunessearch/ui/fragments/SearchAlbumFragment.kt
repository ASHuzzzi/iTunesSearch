package com.example.itunessearch.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itunessearch.R
import com.example.itunessearch.items.Discography
import com.example.itunessearch.ui.viewModel.SearchAlbumViewModel
import com.example.itunessearch.ui.adapter.SearchResultAdapter
import kotlinx.android.synthetic.main.main_fragment.*

class SearchAlbumFragment : Fragment() {

    companion object {
        fun newInstance() =
            SearchAlbumFragment()
    }

    private lateinit var viewModel: SearchAlbumViewModel
    private val adapter =
        SearchResultAdapter(this)
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchAlbumViewModel::class.java)
        recyclerView = recyclerSearchResult
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        buttonSearch.setOnClickListener {
            val inputMethodManager =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
            if (editTextSearch.text.trim().isNotEmpty()) {
                if (!viewModel.checkSearchRequestingRepeat(editTextSearch.text.toString())) {
                    layoutProgress.visibility = View.VISIBLE
                    viewModel.checkNetwork()
                } else {
                    showShortToast(resources.getString(R.string.currentRequestMatchesPrevious))
                }
            } else {
                showShortToast(resources.getString(R.string.enterAlbumSearch))
            }
        }

        viewModel.getNetworkStatus().observe(viewLifecycleOwner, Observer { haveConnection ->
            if (haveConnection) {
                if (!viewModel.checkSearchRequestingRepeat(editTextSearch.text.toString())) {
                    viewModel.setSearchDiscography(editTextSearch.text.toString(), 20)
                }
            } else {
                showShortToast(resources.getString(R.string.noNetworkConnection))
            }
        })
        viewModel.getSearchResult().observe(viewLifecycleOwner, Observer { discography ->
            if (discography.albumList.isNullOrEmpty()) {
                adapter.discography = Discography()
                recyclerView.visibility = View.GONE
                showShortToast(resources.getString(R.string.nothingFound))
            } else {
                adapter.discography = discography
                recyclerView.visibility = View.VISIBLE
            }
            layoutProgress.visibility = View.GONE
        })
    }

    private fun showShortToast(toastMessage: String) {
        val toast = Toast.makeText(activity, toastMessage, Toast.LENGTH_SHORT)
        val layout = toast.view as LinearLayout
        if (layout.childCount > 0) {
            val textView = layout.getChildAt(0) as TextView
            textView. gravity = Gravity.CENTER
        }
        toast.setGravity(Gravity.BOTTOM, 0, 50)
        toast.show()
    }

    fun openAlbumDetailFragment(selectedPositionInAdapter: Int) {
        val albumItem = viewModel.getAlbumItem(selectedPositionInAdapter)
        showShortToast(albumItem.collectionName)
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction
            .replace(R.id.container, AlbumDetailFragment.newInstance(albumItem))
            .addToBackStack(null)
            .commit()
    }
}

