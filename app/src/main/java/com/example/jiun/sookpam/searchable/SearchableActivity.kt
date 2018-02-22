package com.example.jiun.sookpam.searchable

import android.os.Bundle
import com.example.jiun.sookpam.R
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.View
import android.widget.SearchView
import kotlinx.android.synthetic.main.activity_searchable.*


class SearchableActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    var CONTACT_QUERY_LOADER = 0
    var QUERY_KEY = "query"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchable)
        // Get the intent, verify the action and get the query
        val intent = intent
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
        }
        if (intent != null) {
            handleIntent(intent)
        }
        setToolbar()
    }

    override fun onNewIntent(intent: Intent) {
        handleIntent(intent)
    }

    private fun setToolbar() {
        toolbar = search_toolbar
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary))
        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                finish()
            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu!!.findItem(R.id.action_search).actionView as SearchView
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(componentName))
        return true
    }

    /**
     * Assuming this activity was started with a new intent, process the incoming information and
     * react accordingly.
     * @param intent
     */
    private fun handleIntent(intent: Intent) {
        // Special processing of the incoming intent only occurs if the if the action specified
        // by the intent is ACTION_SEARCH.
        if (Intent.ACTION_SEARCH == intent.action) {
            // SearchManager.QUERY is the key that a SearchManager will use to send a query string
            // to an Activity.
            val query = intent.getStringExtra(SearchManager.QUERY)

            // We need to create a bundle containing the query string to send along to the
            // LoaderManager, which will be handling querying the database and returning results.
            val bundle = Bundle()
            bundle.putString(QUERY_KEY, query)

            val loaderCallbacks = ContactablesLoaderCallbacks(this)

            // Start the loader with the new query, and an object that will handle all callbacks.
            loaderManager.restartLoader<Cursor>(CONTACT_QUERY_LOADER, bundle, loaderCallbacks)
        }
    }
}
