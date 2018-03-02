package com.example.jiun.sookpam.searchable

import android.os.Bundle
import com.example.jiun.sookpam.R
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.SearchView
import com.example.jiun.sookpam.RecyclerItemClickListener
import com.example.jiun.sookpam.data.DataItem
import com.example.jiun.sookpam.message.ContentActivity
import com.example.jiun.sookpam.message.ContentItem
import com.example.jiun.sookpam.model.RecordDBManager
import com.example.jiun.sookpam.model.vo.RecordVO
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_searchable.*
import java.util.ArrayList


class SearchableActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var responseList : ArrayList<RecordVO>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchable)
        setToolbar()
    }

    private fun showMessageBody(data: RecordVO) {
        var contentItem : ContentItem = ContentItem()
        contentItem.category = data.category
        contentItem.division = data.division
        contentItem.body = data.message!!.body
        contentItem.phone = data.message!!.phoneNumber
        val intent = Intent(this, ContentActivity::class.java)
        val bundle = Bundle()
        bundle.putSerializable("OBJECT", contentItem)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun setRecyclerView() {
        val recyclerView : RecyclerView = search_recycler_view
        val adapter = SearchableRecyclerAdapter(responseList)
        recyclerView.adapter = adapter

        recyclerView.addOnItemTouchListener(RecyclerItemClickListener(this,
                RecyclerItemClickListener.OnItemClickListener { view, position ->
                    val data = responseList.get(position)
                    showMessageBody(data)
                }))
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
            getQuery(query);
        }
    }

    private fun getQuery(query: String) {
        val recordManager = RecordDBManager(Realm.getDefaultInstance())
        responseList = recordManager.contains(query)
        setRecyclerView()
    }

}
