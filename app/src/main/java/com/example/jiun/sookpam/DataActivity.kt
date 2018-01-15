package com.example.jiun.sookpam

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView

private lateinit var adapter: DataRecyclerAdapter

class DataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)

        val context:Context = applicationContext
        val recyclerView = RecyclerView(context)

        var dataItems:ArrayList<DataItem> = ArrayList()

        adapter = DataRecyclerAdapter(dataItems)
        recyclerView.adapter = adapter

        dataItems.add(DataItem("demo title", "demo body"))
        dataItems.add(DataItem("demo title", "demo body"))
        dataItems.add(DataItem("demo title", "demo body"))
        dataItems.add(DataItem("demo title", "demo body"))
    }

}
