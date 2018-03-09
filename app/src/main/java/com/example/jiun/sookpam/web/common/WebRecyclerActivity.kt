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


class WebRecyclerActivity : AppCompatActivity() {
    private lateinit var service: RecordService
    private lateinit var toolbar: Toolbar
    private lateinit var webRecyclerView: RecyclerView
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
        loadRecords(intent.getStringExtra("category"), intent.getStringExtra("division"))
    }

    private fun initialize() {
        setToolbar()
        webRecyclerView = web_common_record_recycler_view
        webRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        webRecyclerView.adapter = WebCommonRecyclerAdapter(null)
        webRecyclerView.addOnItemTouchListener(RecyclerItemClickListener(applicationContext, RecyclerItemClickListener.OnItemClickListener { _, position ->
            val intent = Intent(applicationContext, WebContentActivity::class.java)
            intent.putExtra("record", records!![position])
            startActivity(intent)
        }))
        categoryTextView = web_common_category_txt
        divisionTextView = web_common_division_txt
        service = ApiUtils.getRecordService()
        backButton = web_common_back_image_btn
        backButton.setOnClickListener { finish() }
        refreshButton = web_common_refresh_img_btn
        refreshButton.setOnClickListener {
            val rotateAnimation = UIAnimation.setRotateAnimation(refreshButton)
            refreshButton.startAnimation(rotateAnimation)
            loadRecords(intent.getStringExtra("category"), intent.getStringExtra("division"))
        }
        errorLinearLayout = web_common_error_linear
        errorImageView = web_common_error_img
        errorTextView = web_common_error_txt
        progressBar = web_common_progressbar
    }

    private fun setToolbar() {
        toolbar = web_common_toolbar
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
                if(division != "취업") {
                    webRecyclerView.adapter = WebCommonRecyclerAdapter(records)
                }
                else {
                    webRecyclerView.adapter = WebCareerRecyclerAdapter(records)
                }
                val context = webRecyclerView.context
                if (records!!.isNotEmpty()) {
                    webRecyclerView.visibility = View.VISIBLE
                    errorLinearLayout.visibility = View.INVISIBLE
                    UIAnimation.setLoadingRecyclerViewAnimation(context, webRecyclerView)
                } else {
                    showNoDataInServer()
                }
                progressBar.visibility = View.INVISIBLE
            }
        })
    }

    private fun showInternetConnectionError() {
        webRecyclerView.visibility = View.INVISIBLE
        errorLinearLayout.visibility = View.VISIBLE
        errorTextView.text = getString(R.string.internet_connect_error)
    }

    private fun showNoDataInServer() {
        webRecyclerView.visibility = View.INVISIBLE
        errorLinearLayout.visibility = View.VISIBLE
        errorTextView.text = getString(R.string.no_data_in_server)
    }
}