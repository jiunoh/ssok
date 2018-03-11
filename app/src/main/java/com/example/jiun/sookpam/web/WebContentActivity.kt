package com.example.jiun.sookpam.web

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.View
import android.webkit.WebView
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
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
    private lateinit var expandableLinear: LinearLayout
    private lateinit var expandableAttachImageView: ImageView
    private lateinit var attachFilesCountTextView: TextView
    private lateinit var attachFilesTextView: TextView

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
        expandableLinear = web_content_attach_linear
        expandableAttachImageView = web_content_attach_expandable_img
        attachFilesCountTextView = web_content_attach_files_count_txt
        attachFilesTextView = web_content_attach_files_txt
        setExpandListener()
        setWebView()
        web_content_back_image_btn.setOnClickListener { finish() }
    }

    private fun setExpandListener() {
        expandableLinear.setOnClickListener {
            if (expandableAttachImageView.rotation == 0.0f) {
                attachFilesTextView.visibility = View.VISIBLE
                expandableAttachImageView.rotation = 180.0f
            } else {
                attachFilesTextView.visibility = View.GONE
                expandableAttachImageView.rotation = 0.0f
            }
        }
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
        checkAttachFilesAndApplyView(record)
    }

    private fun checkAttachFilesAndApplyView(record: RecordResponse) {
        val attachFilesShortCutHtml = WebRecordReformation.getAttachUrlShortcutHtml(record.attach)
        if (attachFilesShortCutHtml == null) {
            expandableAttachImageView.visibility = View.GONE
            expandableLinear.setOnClickListener(null)
            attachFilesCountTextView.text = "0"
        } else {
            attachFilesCountTextView.text = attachFilesShortCutHtml.size.toString()
            fromHtml(attachFilesTextView, attachFilesShortCutHtml)
        }
    }

    @SuppressWarnings("deprecation")
    private fun fromHtml(attachFilesTextView: TextView, attachFilesShortCutHtml: ArrayList<String>) {
        attachFilesTextView.let {
            it.autoLinkMask = 0
            it.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(attachFilesShortCutHtml.joinToString(""), Html.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(attachFilesShortCutHtml.joinToString(""))
            }
            it.movementMethod = LinkMovementMethod.getInstance()
        }
    }

    private fun setToolbar() {
        toolbar = web_content_toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
    }
}
