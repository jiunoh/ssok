package com.example.jiun.sookpam.web.common

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.*
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.*
import retrofit2.*
import com.example.jiun.sookpam.*
import com.example.jiun.sookpam.server.ApiUtils
import com.example.jiun.sookpam.server.RecordResponse
import com.example.jiun.sookpam.server.RecordService
import com.example.jiun.sookpam.web.WebContentActivity
import kotlinx.android.synthetic.main.activity_web_common_recycler.*


class WebCommonRecyclerActivity : AppCompatActivity() {
    private lateinit var service: RecordService
    private lateinit var toolbar: Toolbar
    private lateinit var webCommonRecyclerView: RecyclerView
    private lateinit var categoryTextView: TextView
    private lateinit var divisionTextView: TextView
    private lateinit var backButton: ImageButton
    private lateinit var refreshButton: ImageButton
    private lateinit var errorLinearLayout: LinearLayout
    private lateinit var errorImageView: ImageView
    private lateinit var errorTextView: TextView
    private lateinit var progressBar: ProgressBar
    var records: List<RecordResponse>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_common_recycler)
        initialize()
        loadRecords("중어중문학부", "공지")
    }

    private fun initialize() {
        setToolbar()
        webCommonRecyclerView = client_server_record_recycler_view
        webCommonRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        webCommonRecyclerView.addOnItemTouchListener(RecyclerItemClickListener(applicationContext, RecyclerItemClickListener.OnItemClickListener { _, position ->
            val intent = Intent(applicationContext, WebContentActivity::class.java)
            intent.putExtra("record", records!![position])
            startActivity(intent)
        }))
        categoryTextView = client_server_category_txt
        divisionTextView = client_server_division_txt
        service = ApiUtils.getRecordService()
        backButton = client_server_back_image_btn
        backButton.setOnClickListener { finish() }
        refreshButton = client_server_refresh_img_btn
        refreshButton.setOnClickListener {
            Toast.makeText(applicationContext, "목록 데이터를 갱신합니다.", Toast.LENGTH_SHORT).show()
            val rotateAnimation = UIAnimation.setRotateAnimation(refreshButton)
            refreshButton.startAnimation(rotateAnimation)
            loadRecords("중어중문학부", "공지")
        }
        errorLinearLayout = client_server_error_linear
        errorImageView = client_server_error_img
        errorTextView = client_server_error_txt
        progressBar = client_server_progressbar
    }

    private fun setToolbar() {
        toolbar = client_server_toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
    }

    private fun loadRecords(category: String, division: String) {
        categoryTextView.text = category
        divisionTextView.text = division
        service.getRecords(category, division).enqueue(object : Callback<List<RecordResponse>> {
            override fun onFailure(call: Call<List<RecordResponse>>?, t: Throwable?) {
                showInternetConnectionError()
                progressBar.visibility = View.INVISIBLE
            }

            override fun onResponse(call: Call<List<RecordResponse>>?, response: Response<List<RecordResponse>>?) {
                records = response!!.body()
                webCommonRecyclerView.adapter = WebCommonRecyclerAdapter(records)
                val context = webCommonRecyclerView.context
                if (records!!.isNotEmpty()) {
                    webCommonRecyclerView.visibility = View.VISIBLE
                    errorLinearLayout.visibility = View.INVISIBLE
                    UIAnimation.setLoadingRecyclerViewAnimation(context, webCommonRecyclerView)
                    Toast.makeText(applicationContext, getString(R.string.finish_data_load), Toast.LENGTH_SHORT).show()
                } else {
                    showNoDataInServer()
                }
                progressBar.visibility = View.INVISIBLE
            }
        })
    }

    private fun showInternetConnectionError() {
        Toast.makeText(applicationContext, getString(R.string.internet_connect_error), Toast.LENGTH_SHORT).show()
        webCommonRecyclerView.visibility = View.INVISIBLE
        errorLinearLayout.visibility = View.VISIBLE
        errorTextView.text = getString(R.string.internet_connect_error)
    }

    private fun showNoDataInServer() {
        Toast.makeText(applicationContext, getString(R.string.no_data_in_server), Toast.LENGTH_SHORT).show()
        webCommonRecyclerView.visibility = View.INVISIBLE
        errorLinearLayout.visibility = View.VISIBLE
        errorTextView.text = getString(R.string.no_data_in_server)
    }
}