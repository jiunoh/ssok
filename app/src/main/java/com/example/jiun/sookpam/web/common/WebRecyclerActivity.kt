package com.example.jiun.sookpam.web.common

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.view.ViewCompat
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
    private lateinit var call: Call<List<RecordResponse>>
    private var isRefreshAlreadyStarted = false
    private var isStop = false
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
            if (!isRefreshAlreadyStarted) {
                val rotateAnimation = UIAnimation.setRotateAnimation(refreshButton)
                refreshButton.startAnimation(rotateAnimation)
                loadRecords()
            } else {
                CustomToast.showLastToast(applicationContext, getString(R.string.refresh_already_started))
            }
        }
        errorImageView = web_common_error_img
        errorTextView = web_common_error_txt
        progressBar = web_common_progressbar
        titleTextView = web_title_txt
        val title = "웹 > $category > $division"
        titleTextView.text = title
    }

    override fun onStop() {
        super.onStop()
        isStop = true
        call.cancel()
    }

    private fun setToolbar() {
        appBarLayout = web_common_appbar_layout
        appBarLayout.addOnOffsetChangedListener { _, verticalOffset ->
            if (collapseToolbar.height + verticalOffset < 2 * ViewCompat.getMinimumHeight(collapseToolbar)) {
                backButton.setImageResource(R.drawable.ic_back_button)
                refreshButton.setImageResource(R.drawable.ic_refresh)
            } else {
                backButton.setImageResource(R.drawable.white_back)
                refreshButton.setImageResource(R.drawable.white_refresh)
            }
        }
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
        isRefreshAlreadyStarted = true
        call = service.getRecords(category, division)
        progressBar.visibility = View.VISIBLE
        call.enqueue(object : Callback<List<RecordResponse>> {
            override fun onFailure(call: Call<List<RecordResponse>>?, t: Throwable?) {
                if (!isStop) {
                    showInternetConnectionError()
                }
                progressBar.visibility = View.INVISIBLE
                isRefreshAlreadyStarted = false
            }

            override fun onResponse(call: Call<List<RecordResponse>>?, response: Response<List<RecordResponse>>?) {
                records = response!!.body()
                if (category == "공통" && division == "취업") {
                    webRecyclerView.adapter = WebCareerRecyclerAdapter(records)
                } else {
                    webRecyclerView.adapter = WebCommonRecyclerAdapter(records)
                }
                if (records == null) {
                    call!!.cancel()
                    showServerInvalidError()
                } else {
                    successGettingData()
                }
                progressBar.visibility = View.INVISIBLE
                isRefreshAlreadyStarted = false
            }
        })
    }

    private fun showServerInvalidError() {
        webRecyclerView.visibility = View.INVISIBLE
        errorImageView.visibility = View.VISIBLE
        errorTextView.visibility = View.VISIBLE
        imageFrameLayout.visibility = View.GONE
        errorTextView.text = getString(R.string.server_invalid_error)
        CustomToast.showLastToast(applicationContext, getString(R.string.server_invalid_error))
    }

    private fun successGettingData() {
        if (records!!.isNotEmpty()) {
            appBarLayout.setExpanded(true)
            webRecyclerView.visibility = View.VISIBLE
            titleTextView.visibility = View.GONE
            errorImageView.visibility = View.INVISIBLE
            errorTextView.visibility = View.INVISIBLE
            imageFrameLayout.visibility = View.VISIBLE
            UIAnimation.setLoadingRecyclerViewAnimation(webRecyclerView.context, webRecyclerView)
        } else {
            showNoDataInServer()
        }
        appBarLayout.setExpanded(true)
    }

    private fun showInternetConnectionError() {
        appBarLayout.setExpanded(false, false)
        webRecyclerView.visibility = View.INVISIBLE
        errorImageView.visibility = View.VISIBLE
        errorTextView.visibility = View.VISIBLE
        imageFrameLayout.visibility = View.GONE
        titleTextView.visibility = View.VISIBLE
        errorTextView.text = getString(R.string.internet_connect_error)
        CustomToast.showLastToast(applicationContext, getString(R.string.internet_connect_error))
    }

    private fun showNoDataInServer() {
        appBarLayout.setExpanded(false, false)
        webRecyclerView.visibility = View.INVISIBLE
        errorImageView.visibility = View.VISIBLE
        errorTextView.visibility = View.VISIBLE
        errorTextView.text = getString(R.string.no_data_in_server)
        imageFrameLayout.visibility = View.GONE
        titleTextView.visibility = View.VISIBLE
        CustomToast.showLastToast(applicationContext, getString(R.string.no_data_in_server))
    }
}