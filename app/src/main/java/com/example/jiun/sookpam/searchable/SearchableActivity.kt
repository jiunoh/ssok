package com.example.jiun.sookpam.searchable

import android.app.PendingIntent.getActivity
import android.os.Bundle
import com.example.jiun.sookpam.R
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.support.v4.content.ContextCompat
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.appcompat.R.id.search_close_btn
import android.support.v7.appcompat.R.id.search_mag_icon
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.View
import android.support.v7.widget.SearchView
import android.util.Log
import android.widget.ImageView
import com.example.jiun.sookpam.RecyclerItemClickListener
import com.example.jiun.sookpam.message.ContentActivity
import com.example.jiun.sookpam.message.ContentItem
import com.example.jiun.sookpam.model.vo.RecordVO
import com.example.jiun.sookpam.server.RecordResponse
import com.example.jiun.sookpam.web.WebContentActivity
import kotlinx.android.synthetic.main.activity_searchable.*
import java.util.ArrayList
import android.support.v7.widget.DividerItemDecoration
import android.widget.LinearLayout


class SearchableActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var responseList: ArrayList<SearchItem>
    private lateinit var editsearch: SearchView
    private lateinit var adapter: SearchableRecyclerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchable)
        responseList = ArrayList<SearchItem>()
        setToolbar()
        setRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu!!.findItem(R.id.action_search)
        editsearch = MenuItemCompat.getActionView(searchItem) as SearchView
        editsearch.setIconifiedByDefault(false)
        editsearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                adapter.filter(query)
                editsearch.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        var icon =  editsearch.findViewById(search_mag_icon) as ImageView
        icon.layoutParams = LinearLayout.LayoutParams(0,0)
        icon.visibility = View.GONE
        setCloseEventListener()
        return super.onCreateOptionsMenu(menu)
    }

    private fun setCloseEventListener() {
        val closeButton = editsearch.findViewById(search_close_btn) as ImageView
        closeButton.setOnClickListener(View.OnClickListener {
            Log.v("CLosed", "CLosed")
            editsearch.setQuery("",false)
            adapter.clear()
        })
    }


    private fun setToolbar() {
        toolbar = search_toolbar
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(ContextCompat.getColor(applicationContext, R.color.colorPrimary))
        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(View.OnClickListener {
            finish()
        })
    }

    private fun setRecyclerView() {
        adapter = SearchableRecyclerAdapter(responseList)
        search_recycler_view.adapter = adapter
        search_recycler_view.addItemDecoration(DividerItemDecoration(application, DividerItemDecoration.VERTICAL))
        search_recycler_view.addOnItemTouchListener(RecyclerItemClickListener(this,
                RecyclerItemClickListener.OnItemClickListener { view, position ->
                    val data = responseList.get(position)
                    showMessageBody(data)
                }))
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
            intent = Intent(this, ContentActivity::class.java)
            bundle.putSerializable("OBJECT", contentItem)

        } else {
            intent = Intent(applicationContext, WebContentActivity::class.java)
            bundle.putSerializable("record", data as RecordResponse)
        }
        intent!!.putExtras(bundle)
        startActivity(intent)
    }

}
