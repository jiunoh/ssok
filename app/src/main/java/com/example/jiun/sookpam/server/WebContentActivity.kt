package com.example.jiun.sookpam.server

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.ImageButton
import android.widget.TextView
import com.example.jiun.sookpam.R
import kotlinx.android.synthetic.main.activity_web_content.*

class WebContentActivity : AppCompatActivity() {
    lateinit var toolbar: Toolbar
    lateinit var backButton: ImageButton
    lateinit var categoryTextView: TextView
    lateinit var divisionTextView: TextView
    lateinit var idTextView: TextView
    lateinit var dateTextView: TextView
    lateinit var urlTextView: TextView
    lateinit var titleTextView: TextView
    lateinit var contentTextView: TextView

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
        urlTextView = web_content_url_txt
        titleTextView = web_content_title_txt
        contentTextView = web_content_content_txt
        web_content_back_image_btn.setOnClickListener { finish() }
    }

    private fun setContentData() {
        val intent = intent
        val record = intent.getSerializableExtra("record") as RecordResponse
        categoryTextView.text = record.category
        divisionTextView.text = record.division
        idTextView.text = record.id.toString()
        dateTextView.text = record.date
        urlTextView.text = record.url
        titleTextView.text = record.title
        contentTextView.text = record.content.replace("[\n", "").replace("\n]", "").replace("\n\n", "\n")
    }

    private fun setToolbar() {
        toolbar = web_content_toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
    }
}
