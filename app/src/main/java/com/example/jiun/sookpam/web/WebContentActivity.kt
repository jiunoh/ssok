package com.example.jiun.sookpam.web

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.webkit.WebView
import android.widget.ImageButton
import android.widget.TextView
import com.example.jiun.sookpam.R
import com.example.jiun.sookpam.server.RecordResponse
import kotlinx.android.synthetic.main.activity_web_content.*

class WebContentActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var backButton: ImageButton
    private lateinit var categoryTextView: TextView
    private lateinit var divisionTextView: TextView
    private lateinit var idTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var titleTextView: TextView
    private lateinit var contentWebView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_content)
        initialize()
        setContentData()
    }

    private fun initialize() {
        setToolbar()
        categoryTextView = web_content_category_txt
        divisionTextView = web_content_division_txt
        backButton = web_content_back_image_btn
        idTextView = web_content_id_txt
        dateTextView = web_content_date_txt
        titleTextView = web_content_title_txt
        setWebView()
        web_content_back_image_btn.setOnClickListener { finish() }
    }

    private fun setWebView() {
        contentWebView = web_content_web_view
        contentWebView.settings.builtInZoomControls = true
        contentWebView.settings.supportZoom()
        contentWebView.settings.displayZoomControls = false
        contentWebView.isScrollbarFadingEnabled = true
    }

    private fun setContentData() {
        val intent = intent
        val record = intent.getSerializableExtra("record") as RecordResponse
        categoryTextView.text = record.category
        divisionTextView.text = record.division
        idTextView.text = record.id.toString()
        dateTextView.text = record.date
        titleTextView.text = WebRecordReformation.getTitleSubstring(record.title, record.category, record.division)
        contentWebView.loadData(record.content, "text/html; charset=utf-8", "UTF-8")
    }

    private fun setToolbar() {
        toolbar = web_content_toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
    }
}
