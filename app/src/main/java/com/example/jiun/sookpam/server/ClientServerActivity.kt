package com.example.jiun.sookpam.server

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.*
import android.support.v7.widget.Toolbar
import android.widget.*
import retrofit2.*
import com.example.jiun.sookpam.*
import kotlinx.android.synthetic.main.activity_client_server.*


class ClientServerActivity : AppCompatActivity() {
    private lateinit var service: RecordService
    private lateinit var toolbar: Toolbar
    private lateinit var recordsRecyclerView: RecyclerView
    private lateinit var categoryTextView: TextView
    private lateinit var divisionTextView: TextView
    private lateinit var backButton: ImageButton
    private lateinit var refreshButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_server)
        initialize()
        loadRecords("중어중문학부", "공지")
    }

    private fun initialize() {
        setToolbar()
        recordsRecyclerView = client_server_record_recycler_view
        recordsRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
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
                val records = response!!.body()
                recordsRecyclerView.adapter = RecordRecyclerAdapter(records)
                val context = recordsRecyclerView.context
                UIAnimation.setLoadingRecyclerViewAnimation(context, recordsRecyclerView)
                Toast.makeText(applicationContext, "데이터 로드를 완료했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showErrorMessage() {
        Toast.makeText(this, "데이터 불러오기에 실패했습니다. 인터넷 연결상태를 확인해주세요.", Toast.LENGTH_SHORT).show()
    }
}