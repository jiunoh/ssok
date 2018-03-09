package com.example.jiun.sookpam.web.common

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
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
    private lateinit var backButton: ImageButton
    private lateinit var refreshButton: ImageButton
    private lateinit var errorImageView: ImageView
    private lateinit var errorTextView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var collapseToolbar: CollapsingToolbarLayout
    private lateinit var appBarLayout: AppBarLayout
    private lateinit var imageFrameLayout: FrameLayout
    private lateinit var titleTextView: TextView
    private lateinit var category: String
    private lateinit var division: String
    private var records: List<RecordResponse>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_common_recycler)
        category = intent.getStringExtra("category")
        division = intent.getStringExtra("division")
        initialize()
        loadRecords()
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
        service = ApiUtils.getRecordService()
        backButton = web_common_back_image_btn
        backButton.setOnClickListener { finish() }
        refreshButton = web_common_refresh_img_btn
        refreshButton.setOnClickListener {
            val rotateAnimation = UIAnimation.setRotateAnimation(refreshButton)
            refreshButton.startAnimation(rotateAnimation)
            loadRecords()
        }
        errorImageView = web_common_error_img
        errorTextView = web_common_error_txt
        progressBar = web_common_progressbar
        titleTextView = web_title_txt
        val title = "웹 > $category > $division"
        titleTextView.text = title
    }

    private fun setToolbar() {
        appBarLayout = web_common_appbar_layout
        toolbar = web_common_toolbar
        setSupportActionBar(toolbar)
        collapseToolbar = web_common_collapsing
        collapseToolbar.title = "웹 > $category > $division"
        collapseToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar)
        collapseToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar)
        web_common_toolbar_img.setImageResource(intent.getIntExtra("background", 0))
        imageFrameLayout = web_common_img_frame
    }

    private fun loadRecords() {
        service.getRecords(category, division).enqueue(object : Callback<List<RecordResponse>> {
            override fun onFailure(call: Call<List<RecordResponse>>?, t: Throwable?) {
                showInternetConnectionError()
                progressBar.visibility = View.INVISIBLE
            }

            override fun onResponse(call: Call<List<RecordResponse>>?, response: Response<List<RecordResponse>>?) {
                records = response!!.body()
                if (division != "취업") {
                    webRecyclerView.adapter = WebCommonRecyclerAdapter(records)
                } else {
                    webRecyclerView.adapter = WebCareerRecyclerAdapter(records)
                }
                val context = webRecyclerView.context
                if (records!!.isNotEmpty()) {
                    appBarLayout.setExpanded(true)
                    titleTextView.visibility = View.GONE
                    errorImageView.visibility = View.INVISIBLE
                    errorTextView.visibility = View.INVISIBLE
                    imageFrameLayout.visibility = View.VISIBLE
                    UIAnimation.setLoadingRecyclerViewAnimation(context, webRecyclerView)
                } else {
                    showNoDataInServer()
                }
                appBarLayout.setExpanded(true)
                progressBar.visibility = View.INVISIBLE
            }
        })
    }

    private fun showInternetConnectionError() {
        appBarLayout.setExpanded(false, false)
        errorImageView.visibility = View.VISIBLE
        errorTextView.visibility = View.VISIBLE
        imageFrameLayout.visibility = View.GONE
        titleTextView.visibility = View.VISIBLE
        errorTextView.text = getString(R.string.internet_connect_error)
    }

    private fun showNoDataInServer() {
        appBarLayout.setExpanded(false, false)
        errorImageView.visibility = View.VISIBLE
        errorTextView.visibility = View.VISIBLE
        imageFrameLayout.visibility = View.GONE
        titleTextView.visibility = View.VISIBLE
        errorTextView.text = getString(R.string.no_data_in_server)
    }
}