package com.example.jiun.sookpam

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.jiun.sookpam.user.setting.UserSettingLibrary
import com.example.jiun.sookpam.user.major.MajorActivity
import com.example.jiun.sookpam.util.SharedPreferenceUtil
import kotlinx.android.synthetic.main.fragment_my_info.*

class MyInfoFragment : Fragment() {
    private lateinit var studentYearSpinner: Spinner
    private lateinit var yearSpinnerArrayAdapter: ArrayAdapter<String>
    private lateinit var studentGradeSpinner: Spinner
    private lateinit var gradeSpinnerArrayAdapter: ArrayAdapter<String>
    private lateinit var studentMajorButton: Button
    private lateinit var studentMajorRecyclerView: RecyclerView
    private var selectedMajors = ArrayList<String>()
    private lateinit var schoolScholarshipCheckBox: CheckBox
    private lateinit var externalScholarshipCheckBox: CheckBox
    private lateinit var governmentScholarshipCheckBox: CheckBox
    private lateinit var studentStatusRadioGroup: RadioGroup
    private lateinit var statusInRadioButton: RadioButton
    private lateinit var statusOutRadioButton: RadioButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_my_info, container, false)
    }

    override fun onStart() {
        super.onStart()
        initialize()
        loadUserInfoData()
    }

    private fun initialize() {
        initializeYearSpinner()
        initializeGradeSpinner()
        initializeMajors()
        initializeScholarshipCheckBoxes()
        initializeStatusRadio()
    }

    private fun initializeGradeSpinner() {
        studentGradeSpinner = my_info_student_grade_spinner
        gradeSpinnerArrayAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.student_grade))
        UserSettingLibrary.setSpinnerAdapter(studentGradeSpinner, gradeSpinnerArrayAdapter, UserSettingLibrary.STUDENT_GRADE, context!!)
    }

    private fun initializeYearSpinner() {
        studentYearSpinner = my_info_student_year_spinner
        yearSpinnerArrayAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.student_year))
        UserSettingLibrary.setSpinnerAdapter(studentYearSpinner, yearSpinnerArrayAdapter, UserSettingLibrary.STUDENT_YEAR, context!!)
    }

    private fun initializeMajors() {
        studentMajorButton = my_info_major_btn
        studentMajorButton.setOnClickListener {
            val intent = Intent(context, MajorActivity::class.java)
            selectedMajors = UserSettingLibrary.getSelectedMajors(context!!)
            intent.putExtra("selectedMajors", selectedMajors)
            startActivityForResult(intent, UserSettingLibrary.MAJOR_REQUEST_CODE)
        }
        studentMajorRecyclerView = my_info_majors_recycler_view
        studentMajorRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun initializeScholarshipCheckBoxes() {
        schoolScholarshipCheckBox = my_info_school_scholarship_checkbox
        UserSettingLibrary.setCheckBoxListener(schoolScholarshipCheckBox, context!!)
        externalScholarshipCheckBox = my_info_external_scholarship_checkbox
        UserSettingLibrary.setCheckBoxListener(externalScholarshipCheckBox, context!!)
        governmentScholarshipCheckBox = my_info_government_scholarship_checkbox
        UserSettingLibrary.setCheckBoxListener(externalScholarshipCheckBox, context!!)
    }

    private fun initializeStatusRadio() {
        studentStatusRadioGroup = my_info_status_radio_group
        statusInRadioButton = my_info_status_in_radio_btn
        statusOutRadioButton = my_info_status_out_radio_btn
        UserSettingLibrary.setRadioButtonListener(studentStatusRadioGroup, statusInRadioButton, statusOutRadioButton, context!!)
    }

    private fun loadUserInfoData() {
        val selectedYear = SharedPreferenceUtil.get(context, UserSettingLibrary.STUDENT_YEAR, "18")
        studentYearSpinner.setSelection(yearSpinnerArrayAdapter.getPosition(selectedYear))
        val selectedGrade = SharedPreferenceUtil.get(context, UserSettingLibrary.STUDENT_GRADE, "1 학년")
        studentGradeSpinner.setSelection(gradeSpinnerArrayAdapter.getPosition(selectedGrade))
        UserSettingLibrary.loadMajors(studentMajorButton, studentMajorRecyclerView, context!!)
        UserSettingLibrary.loadCheckBoxData(schoolScholarshipCheckBox, context!!)
        UserSettingLibrary.loadCheckBoxData(externalScholarshipCheckBox, context!!)
        UserSettingLibrary.loadCheckBoxData(governmentScholarshipCheckBox, context!!)
        UserSettingLibrary.loadRadioGroupData(studentStatusRadioGroup, statusInRadioButton, statusOutRadioButton, context!!)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            UserSettingLibrary.loadMajors(studentMajorButton, studentMajorRecyclerView, context!!)
        }
    }
}
