package com.example.jiun.sookpam.searchable

import android.app.ListActivity
import android.os.Bundle
import com.example.jiun.sookpam.R
import android.app.SearchManager
import android.content.Intent
import android.support.v4.view.MenuItemCompat.getActionView
import android.content.Context.SEARCH_SERVICE
import android.view.MenuInflater





class SearchableActivity : ListActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchable)
        // Get the intent, verify the action and get the query
        val intent = intent
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
        }
    }


}
