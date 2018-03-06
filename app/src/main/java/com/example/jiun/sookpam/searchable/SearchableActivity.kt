package com.example.jiun.sookpam.searchable

import android.os.Bundle
import com.example.jiun.sookpam.R
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.support.v7.app.AppCompatActivity
import android.widget.SearchView
import com.example.jiun.sookpam.RecyclerItemClickListener
import com.example.jiun.sookpam.message.ContentActivity
import com.example.jiun.sookpam.message.ContentItem
import com.example.jiun.sookpam.model.vo.RecordVO
import com.example.jiun.sookpam.server.RecordResponse
import com.example.jiun.sookpam.server.WebContentActivity
import kotlinx.android.synthetic.main.activity_searchable.*
import java.util.ArrayList


class SearchableActivity : AppCompatActivity(), SearchView.OnQueryTextListener, SearchView.OnCloseListener {
    private lateinit var responseList: ArrayList<SearchItem>
    private lateinit var editsearch: SearchView
    private lateinit var adapter: SearchableRecyclerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchable)
        responseList = ArrayList<SearchItem>()
        setRecyclerView()
        editsearch = search_view
        editsearch.setOnQueryTextListener(this);

        if (isNetWork()) {
        } else {
            //error
        }
    }

    private fun isNetWork(): Boolean {
        var connectManager: ConnectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetwork: NetworkInfo? = connectManager.getActiveNetworkInfo()
        var isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected
    }

    private fun setRecyclerView() {
        adapter = SearchableRecyclerAdapter(responseList)
        search_recycler_view.adapter = adapter

        search_recycler_view.addOnItemTouchListener(RecyclerItemClickListener(this,
                RecyclerItemClickListener.OnItemClickListener { view, position ->
                    val data = responseList.get(position)
                    showMessageBody(data)
                }))
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        adapter.filter(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    override fun onClose(): Boolean {
        adapter.clear()
        return false
    }

    private fun showMessageBody(data: SearchItem) {
        val bundle = Bundle()
        var intent: Intent? = null
        if (data is RecordVO) {
            var contentItem: ContentItem = ContentItem()
            contentItem.category = data.category
            contentItem.division = data.division
            contentItem.body = data.message!!.body
            contentItem.phone = data.message!!.phoneNumber
            val intent = Intent(this, ContentActivity::class.java)
            bundle.putSerializable("OBJECT", contentItem)

        } else {
            val intent = Intent(applicationContext, WebContentActivity::class.java)
            bundle.putSerializable("record", data as RecordResponse)
        }
        intent!!.putExtras(bundle)
        startActivity(intent)
    }

}
