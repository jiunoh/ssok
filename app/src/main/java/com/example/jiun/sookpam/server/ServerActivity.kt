package com.example.jiun.sookpam.server

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.jiun.sookpam.R
import retrofit2.Call
import kotlinx.android.synthetic.main.activity_server.*
import retrofit2.Callback
import retrofit2.Response

class ServerActivity : AppCompatActivity() {
    lateinit var service: RecordService
    lateinit var resultText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_server)
        initialize()
        service = ApiUtils.getRecordService()
        loadRecords("교육학부", "공지")
    }

    private fun initialize() {
        resultText = server_results_txt
    }

    private fun loadRecords(category: String, division: String) {
        service.getRecords(category, division).enqueue(object : Callback<List<RecordResponse>> {
            override fun onFailure(call: Call<List<RecordResponse>>?, t: Throwable?) {
                showErrorMessage()
            }

            override fun onResponse(call: Call<List<RecordResponse>>?, response: Response<List<RecordResponse>>?) {
                val result = response!!.body().toString()
                resultText.text = result
            }
        })

    }

    private fun showErrorMessage() {
        Toast.makeText(this, "데이터 불러오기에 실패했습니다.", Toast.LENGTH_SHORT).show()
    }
}