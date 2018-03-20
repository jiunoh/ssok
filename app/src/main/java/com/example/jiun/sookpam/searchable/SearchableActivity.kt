package com.example.jiun.sookpam.searchable

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.appcompat.R.id.search_close_btn
import android.support.v7.appcompat.R.id.search_mag_icon
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.*
import com.example.jiun.sookpam.R
import com.example.jiun.sookpam.RecyclerItemClickListener
import com.example.jiun.sookpam.model.DualModel
import com.example.jiun.sookpam.model.vo.RecordVO
import com.example.jiun.sookpam.server.ApiUtils
import com.example.jiun.sookpam.server.RecordResponse
import com.example.jiun.sookpam.util.MsgContentGenerator
import com.example.jiun.sookpam.web.WebContentActivity
import kotlinx.android.synthetic.main.activity_searchable.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class SearchableActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var modelList: List<DualModel>
    private lateinit var editsearch: SearchView
    private lateinit var adapter: SearchableRecyclerAdapter
    private lateinit var errorLinearLayout: LinearLayout
    private lateinit var errorImageView: ImageView
    private lateinit var errorTextView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var keyword1: TextView
    private lateinit var keyword2: TextView
    private lateinit var keyword3: TextView
    private lateinit var keyword4: TextView
    private lateinit var similarKeywords: LinearLayout
    private lateinit var searchQuery: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchable)
        modelList = ArrayList()
        setToolbar()
        setRecyclerView()
        setRestOfTheView()
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
        adapter = SearchableRecyclerAdapter(modelList)
        search_recycler_view.adapter = adapter
        search_recycler_view.visibility = View.VISIBLE
        search_recycler_view.bringToFront()
        search_recycler_view.addOnItemTouchListener(RecyclerItemClickListener(this,
                RecyclerItemClickListener.OnItemClickListener { view, position ->
                    val data = modelList.get(position)
                    showMessageBody(data)
                }))
    }

    private fun setRestOfTheView() {
        errorLinearLayout = web_common_error_linear
        errorLinearLayout.visibility = View.INVISIBLE
        errorImageView = web_common_error_img
        errorTextView = web_common_error_txt
        progressBar = web_common_progressbar
        progressBar.visibility = View.INVISIBLE
    }

    override fun onResume() {
        super.onResume()
        errorLinearLayout.visibility = View.INVISIBLE
        search_recycler_view.visibility = View.VISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu!!.findItem(R.id.action_search)

        similarKeywords = findViewById<LinearLayout>(R.id.search_keyword_layout)
        similarKeywords.visibility = View.VISIBLE

        initializeKeywordsAndSetListener()
        editsearch = MenuItemCompat.getActionView(searchItem) as SearchView
        editsearch.setIconifiedByDefault(false)
        editsearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                similarKeywords.visibility = View.INVISIBLE
                search_recycler_view.visibility = View.VISIBLE
                errorLinearLayout.visibility = View.INVISIBLE
                search(query)
                progressBar.visibility = View.VISIBLE
                editsearch.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        val icon = editsearch.findViewById(search_mag_icon) as ImageView
        icon.layoutParams = LinearLayout.LayoutParams(0, 0)
        icon.visibility = View.GONE
        setCloseEventListener()

        return super.onCreateOptionsMenu(menu)
    }

    private fun setCloseEventListener() {
        val closeButton = editsearch.findViewById(search_close_btn) as ImageView
        closeButton.setOnClickListener(View.OnClickListener {
            cleanRecyclerView()
            editsearch.setQuery("", false)
            adapter.clear()
        })
    }


    private fun cleanRecyclerView() {
        search_recycler_view.visibility = View.VISIBLE
        errorLinearLayout.visibility = View.INVISIBLE
        requestSearchKeywords(searchQuery)
        similarKeywords.visibility = View.VISIBLE
    }

    private fun search(query: String) {
        val service = ApiUtils.getSearchableService()
        val query = query.replace("\\s+".toRegex(), "--").replace("/","__")
        searchQuery = query
        service.getItems(query).enqueue(object : Callback<List<RecordResponse>> {
            override fun onResponse(call: Call<List<RecordResponse>>, response: Response<List<RecordResponse>>) {
                if (!response.isSuccessful) {
                    Log.v("response", " disconnected")
                    return
                }
                progressBar.visibility = View.INVISIBLE
                val records = response.body()
                adapter.searchInRealm(query)
                adapter.add(records)
                if(adapter.modelList.isEmpty())
                    showNoData()
            }

            override fun onFailure(call: Call<List<RecordResponse>>, t: Throwable) {
                Log.v("onFailure:", "onFailure")
            }
        })
    }

    private fun showMessageBody(data: DualModel) {
        val bundle = Bundle()
        if (data is RecordVO) {
            MsgContentGenerator.showMessageBody(baseContext, data)
        } else {
            val intent = Intent(applicationContext, WebContentActivity::class.java)
            bundle.putSerializable("record", data as RecordResponse)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }

    fun showNoData() {
        Toast.makeText(applicationContext, getString(R.string.no_data_in_server), Toast.LENGTH_SHORT).show()
        search_recycler_view.visibility = View.INVISIBLE
        errorLinearLayout.visibility = View.VISIBLE
        errorTextView.text = getString(R.string.no_data_in_server)
    }

    private fun initializeKeywordsAndSetListener() {
        keyword1 = findViewById(R.id.search_keyword_1)
        keyword2 = findViewById(R.id.search_keyword_2)
        keyword3 = findViewById(R.id.search_keyword_3)
        keyword4 = findViewById(R.id.search_keyword_4)

        keyword1.setOnClickListener{
            var query = keyword1.text.toString()
            editsearch.setQuery(query, true)
        }
        keyword2.setOnClickListener{
            var query = keyword2.text.toString()
            editsearch.setQuery(query, true)
        }
        keyword3.setOnClickListener{
            var query = keyword3.text.toString()
            editsearch.setQuery(query, true)
        }
        keyword4.setOnClickListener{
            var query = keyword4.text.toString()
            editsearch.setQuery(query, true)
        }
    }

    private fun requestSearchKeywords(query: String) {
        val service = ApiUtils.getSearchKeywordService()
        service.getItems(query).enqueue(object : Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                if (!response.isSuccessful) {
                    Log.v("response", " disconnected")
                    return
                }
                setSearchKeywords(response.body())
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                Log.v("onFailure:", "onFailure")
            }
        })
    }

    private fun setSearchKeywords(response: List<String>?) {
        val keywordViews:IntArray = intArrayOf(R.id.search_keyword_1, R.id.search_keyword_2, R.id.search_keyword_3, R.id.search_keyword_4)

        var i:Int = 0
        var text:String = ""
        for (view in keywordViews) {
            var textView:TextView = findViewById<TextView>(view)
            text = response!!.get(i)
            if (text == null)
                ;
            else
                textView.text = text
            i++
        }
    }

}