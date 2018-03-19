package com.example.jiun.sookpam.setting

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.LinearLayout

import com.example.jiun.sookpam.R
import com.example.jiun.sookpam.category.CategoryActivity

class InfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        val settingCategory = findViewById<LinearLayout>(R.id.setting_category)
        settingCategory.setOnClickListener { go() }
    }

    private fun go() {
        val intent = Intent(this, CategoryActivity::class.java)
        startActivity(intent)
    }
}
