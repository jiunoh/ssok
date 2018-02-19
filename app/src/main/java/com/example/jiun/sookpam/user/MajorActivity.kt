package com.example.jiun.sookpam.user

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.ImageButton
import com.example.jiun.sookpam.R
import kotlinx.android.synthetic.main.activity_major.*

class MajorActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var backImageButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_major)
        initialize()
    }

    private fun initialize() {
        setToolbar()
        backImageButton = major_back_image_btn
        backImageButton.setOnClickListener {
            finish()
        }
    }

    private fun setToolbar() {
        toolbar = major_toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
    }
}
