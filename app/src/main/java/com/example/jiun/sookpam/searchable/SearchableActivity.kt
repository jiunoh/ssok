package com.example.jiun.sookpam.searchable

import android.os.Bundle
import com.example.jiun.sookpam.R
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
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


class SearchableActivity : AppCompatActivity(), SearchView.OnQueryTextListener, SearchView.OnCloseListener {
    private lateinit var responseList: ArrayList<RecordVO>
    private lateinit var editsearch: SearchView
    private lateinit var adapter: SearchableRecyclerAdapter
    private lateinit var connectManager: ConnectivityManager


    override  fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchable)
        responseList = ArrayList<RecordVO>()
        if (isNetWork()) {
            setRecyclerView()
            editsearch = search_view
            editsearch.setOnQueryTextListener(this);
        }
        else {

        }
    }

    private fun isNetWork(): Boolean {
        connectManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val isMobileAvailable = connectManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isAvailable
        val isMobileConnect = connectManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting
        val isWifiAvailable = connectManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isAvailable
        val isWifiConnect = connectManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting

        return isWifiAvailable && isWifiConnect || isMobileAvailable && isMobileConnect
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
        Log.v("onClose():", "true")
        adapter.clear()
        return false
    }

    private fun showMessageBody(data: RecordVO) {
        var contentItem: ContentItem = ContentItem()
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
