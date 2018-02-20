package com.example.jiun.sookpam.user.major

import android.app.Activity
import android.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.example.jiun.sookpam.R
import com.example.jiun.sookpam.util.SharedPreferenceUtil
import com.github.aakira.expandablelayout.Utils
import kotlinx.android.synthetic.main.activity_major.*

class MajorActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var backImageButton: ImageButton
    private lateinit var collegeRecyclerView: RecyclerView
    private lateinit var confirmButton: Button
    private var data: ArrayList<MajorItemModel> = ArrayList()
    private lateinit var selectedMajors: ArrayList<String>

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
            if (doesSelectedMajorsChanged()) {
                showResetAlert()
            } else resetUnsavedMajorsAndFinish()
        }

        confirmButton = major_select_confirm_btn
        confirmButton.setOnClickListener {
            setResult(Activity.RESULT_OK)
            Toast.makeText(applicationContext, getString(R.string.save_toast), Toast.LENGTH_SHORT).show()
            finish()
        }

        selectedMajors = intent.extras.getStringArrayList("selectedMajors")
    }

    override fun onBackPressed() {
        if (doesSelectedMajorsChanged()) {
            showResetAlert()
        } else resetUnsavedMajorsAndFinish()
    }

    private fun doesSelectedMajorsChanged(): Boolean {
        return MajorList.collegeAndMajors
                .flatMap { it }
                .any { SharedPreferenceUtil.get(applicationContext, it, false) && it !in selectedMajors }
    }

    private fun showResetAlert() {
        AlertDialog.Builder(this@MajorActivity).apply {
            setTitle(getString(R.string.major_alert_title))
            setMessage(getString(R.string.major_alert_message))
            setPositiveButton(getString(R.string.yes), { dialog, _ ->
                dialog.dismiss()
                resetUnsavedMajorsAndFinish()
            })
            setNegativeButton(getString(R.string.no), { dialog, _ ->
                dialog.dismiss()
            })
            show()
        }
    }

    private fun resetUnsavedMajorsAndFinish() {
        MajorList.collegeAndMajors
                .flatMap { it }
                .filter { SharedPreferenceUtil.get(applicationContext, it, false) && it !in selectedMajors }
                .forEach { SharedPreferenceUtil.set(applicationContext, it, false) }
        setResult(Activity.RESULT_CANCELED)
        finish()
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
