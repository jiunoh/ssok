package com.example.jiun.sookpam.user.major

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.ImageButton
import com.example.jiun.sookpam.R
import com.github.aakira.expandablelayout.Utils
import kotlinx.android.synthetic.main.activity_major.*

class MajorActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var backImageButton: ImageButton
    private lateinit var collegeRecyclerView:RecyclerView
    private var data:ArrayList<MajorItemModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_major)
        initialize()
        createList()
    }

    private fun initialize() {
        setToolbar()
        collegeRecyclerView = college_recycler_view
        collegeRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        collegeRecyclerView.addItemDecoration(DividerItemDecoration(applicationContext, LinearLayoutManager(applicationContext).orientation))

        backImageButton = major_back_image_btn
        backImageButton.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    private fun createList() {
        data.add(getMajorItemModel(getString(R.string.college_liberalarts)))
        data.add(getMajorItemModel(getString(R.string.college_science)))
        data.add(getMajorItemModel(getString(R.string.college_engineering)))
        data.add(getMajorItemModel(getString(R.string.college_human_ecology)))
        data.add(getMajorItemModel(getString(R.string.college_social_sciences)))
        data.add(getMajorItemModel(getString(R.string.college_law)))
        data.add(getMajorItemModel(getString(R.string.college_economics_business_administration)))
        data.add(getMajorItemModel(getString(R.string.college_music)))
        data.add(getMajorItemModel(getString(R.string.college_pharmacy)))
        data.add(getMajorItemModel(getString(R.string.college_fine_art)))
        data.add(getMajorItemModel(getString(R.string.school_global_service)))
        data.add(getMajorItemModel(getString(R.string.school_english)))
        data.add(getMajorItemModel(getString(R.string.school_communication_media)))
        collegeRecyclerView.adapter = CollegeRecyclerAdapter(data)
    }

    private fun getMajorItemModel(college: String): MajorItemModel {
        return MajorItemModel(college, Utils.createInterpolator(Utils.ACCELERATE_DECELERATE_INTERPOLATOR))
    }

    private fun setToolbar() {
        toolbar = major_toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
    }
}
