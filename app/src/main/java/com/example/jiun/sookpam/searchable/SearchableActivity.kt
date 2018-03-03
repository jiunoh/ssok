package com.example.jiun.sookpam.searchable

import android.os.Bundle
import com.example.jiun.sookpam.R
import android.app.SearchManager
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.SearchView
import com.example.jiun.sookpam.RecyclerItemClickListener
import com.example.jiun.sookpam.message.ContentActivity
import com.example.jiun.sookpam.message.ContentItem
import com.example.jiun.sookpam.model.RecordDBManager
import com.example.jiun.sookpam.model.vo.RecordVO
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_searchable.*
import java.util.ArrayList


class SearchableActivity : AppCompatActivity() ,  SearchView.OnQueryTextListener {
    private lateinit var responseList : ArrayList<RecordVO>
    private lateinit var editsearch : SearchView
    private lateinit var adapter :SearchableRecyclerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchable)
        responseList = ArrayList<RecordVO>()
        setRecyclerView()
        editsearch = search_view
        editsearch.setOnQueryTextListener(this);
    }

    private fun setRecyclerView() {
        val recyclerView : RecyclerView = search_recycler_view
        adapter = SearchableRecyclerAdapter(responseList)
        recyclerView.adapter = adapter

        recyclerView.addOnItemTouchListener(RecyclerItemClickListener(this,
                RecyclerItemClickListener.OnItemClickListener { view, position ->
                    val data = responseList.get(position)
                    showMessageBody(data)
                }))
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false;
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        Log.v("onQueryTextChange: ", newText)
        adapter.filter(newText)
        return false
    }

    override fun onNewIntent(intent: Intent) {
        handleIntent(intent)
    }

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

}
