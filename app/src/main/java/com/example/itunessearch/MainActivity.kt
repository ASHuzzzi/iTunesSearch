package com.example.itunessearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.itunessearch.ui.fragments.SearchAlbumFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SearchAlbumFragment.newInstance())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onBackPressed() {
        val backStackEntryCount = supportFragmentManager.backStackEntryCount
        if (backStackEntryCount == 1) {
            finish()
        } else {
            supportFragmentManager.popBackStack()
        }
    }
}
