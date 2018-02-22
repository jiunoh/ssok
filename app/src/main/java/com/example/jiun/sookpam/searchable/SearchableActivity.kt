package com.example.jiun.sookpam.searchable

import android.os.Bundle
import com.example.jiun.sookpam.R
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.View
import android.widget.SearchView
import com.example.jiun.sookpam.RecyclerItemClickListener
import com.example.jiun.sookpam.data.DataItem
import com.example.jiun.sookpam.data.DataRecyclerAdapter
import com.example.jiun.sookpam.message.ContentActivity
import com.example.jiun.sookpam.model.ContactDBManager
import com.example.jiun.sookpam.model.RecordDBManager
import com.example.jiun.sookpam.util.SharedPreferenceUtil
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_searchable.*
import java.util.ArrayList


class SearchableActivity : AppCompatActivity() {
    private lateinit  var categoryManager: RecordDBManager
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
            doMySearch(query);
        }
        if (intent != null) {
            handleIntent(intent)
        }
        setToolbar()
        val recyclerView : RecyclerView = search_recycler_view
        val dataItems = ArrayList<DataItem>()
        //getDataItems
        //dataItems.add(dataItem)

        val adapter = SearchableRecyclerAdapter(dataItems)
        recyclerView.adapter = adapter

        recyclerView.addOnItemTouchListener(RecyclerItemClickListener(this,
                RecyclerItemClickListener.OnItemClickListener { view, position ->
                    val data = dataItems.get(position)
                    showMessageBody(data)
                }))
    }

    private fun showMessageBody(data: DataItem) {
        val intent = Intent(this, ContentActivity::class.java)
        intent.putExtra("title", data.title)
        intent.putExtra("date", data.body)
        startActivity(intent)
    }

    override fun onSearchRequested(): Boolean {
        //pauseSomeStuff()
        return super.onSearchRequested()
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
        searchView.setIconifiedByDefault(false)
        searchView.setSubmitButtonEnabled(true)
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
            doMySearch(query);
        }
    }

    private fun doMySearch(division: String) {

    }


}
