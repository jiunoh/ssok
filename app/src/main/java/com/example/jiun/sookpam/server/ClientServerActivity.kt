package com.example.jiun.sookpam.server

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.jiun.sookpam.R
import com.example.jiun.sookpam.RecyclerItemClickListener
import retrofit2.Call
import kotlinx.android.synthetic.main.activity_client_server.*
import retrofit2.Callback
import retrofit2.Response

class ClientServerActivity : AppCompatActivity() {
    private lateinit var service: RecordService
    lateinit var toolbar: Toolbar
    lateinit var recordsRecyclerView: RecyclerView
    lateinit var categoryTextView: TextView
    lateinit var divisionTextView: TextView
    lateinit var backButton: ImageButton
    var records: List<RecordResponse>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_server)
        initialize()
        loadRecords("교육학부", "공지")
    }

    private fun initialize() {
        setToolbar()
        recordsRecyclerView = client_server_record_recycler_view
        recordsRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recordsRecyclerView.addOnItemTouchListener(RecyclerItemClickListener(applicationContext, RecyclerItemClickListener.OnItemClickListener { _, position ->
            val intent = Intent(applicationContext, WebContentActivity::class.java)
            intent.putExtra("record", records!![position])
            startActivity(intent)
        }))
        categoryTextView = client_server_category_txt
        divisionTextView = client_server_division_txt
        service = ApiUtils.getRecordService()
        backButton = client_server_back_image_btn
        backButton.setOnClickListener { finish() }
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
                showErrorMessage()
            }

            override fun onResponse(call: Call<List<RecordResponse>>?, response: Response<List<RecordResponse>>?) {
                records = response!!.body()
                recordsRecyclerView.adapter = RecordRecyclerAdapter(records)
            }
        })

    }

    private fun showErrorMessage() {
        Toast.makeText(this, "데이터 불러오기에 실패했습니다.", Toast.LENGTH_SHORT).show()
    }
}