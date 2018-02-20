package com.example.jiun.sookpam.user.major

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.ImageButton
import com.example.jiun.sookpam.R
import com.github.aakira.expandablelayout.Utils
import kotlinx.android.synthetic.main.activity_college_recycler_view.*

class MajorRecyclerViewActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var backImageButton: ImageButton
    private lateinit var recyclerView:RecyclerView
    private var data:ArrayList<MajorItemModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_college_recycler_view)
        initialize()
        createList()
    }

    private fun initialize() {
        setToolbar()
        recyclerView = college_recycler_view
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.addItemDecoration(DividerItemDecoration(applicationContext, LinearLayoutManager(applicationContext).orientation))
        backImageButton = major_back_image_btn
        backImageButton.setOnClickListener {
            finish()
        }
    }

    private fun createList() {
        val list = ArrayList<String>()
        list.add("학과")
        data.add(MajorItemModel("단과대학", list, Utils.createInterpolator(Utils.ACCELERATE_DECELERATE_INTERPOLATOR)))
        recyclerView.adapter = CollegeRecyclerAdapter(data)
    }

    private fun setToolbar() {
        toolbar = major_toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
    }
}
