package com.example.jiun.sookpam.user

import android.app.Activity
import android.support.v4.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import com.example.jiun.sookpam.R
import com.example.jiun.sookpam.util.SharedPreferenceUtil
import kotlinx.android.synthetic.main.fragment_user_info1.*

class UserInfo1Fragment : Fragment() {
    private var userInfo1View: View? = null
    private var userInfo1Activity: Activity? = null
    private var userInfo1Context: Context? = null
    private lateinit var studentYearSpinner: Spinner
    private lateinit var studentGradeSpinner: Spinner
    private lateinit var majorSelectingButton: Button
    private lateinit var yearSpinnerArrayAdapter: ArrayAdapter<String>
    private lateinit var gradeSpinnerArrayAdapter: ArrayAdapter<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        userInfo1View = inflater.inflate(R.layout.fragment_user_info1, container, false)
        userInfo1Activity = activity
        userInfo1Context = userInfo1View!!.context
        yearSpinnerArrayAdapter = ArrayAdapter(userInfo1Context, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.student_year))
        gradeSpinnerArrayAdapter = ArrayAdapter(userInfo1Context, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.student_grade))
        return userInfo1View
    }

    override fun onStart() {
        super.onStart()
        initialize()
    }

    override fun onResume() {
        super.onResume()
        loadPage1Data(studentYearSpinner, studentGradeSpinner)
    }

    private fun initialize() {
        studentYearSpinner = user_info1_student_year_spinner
        studentGradeSpinner = user_info1_student_grade_spinner
        setSpinnerAdapter(studentYearSpinner, yearSpinnerArrayAdapter, STUDENT_YEAR)
        setSpinnerAdapter(studentGradeSpinner, gradeSpinnerArrayAdapter, STUDENT_GRADE)
        majorSelectingButton = user_info1_major_btn
    }

    private fun setSpinnerAdapter(spinner: Spinner, spinnerArrayAdapter: ArrayAdapter<String>, name: String) {
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinner.adapter = spinnerArrayAdapter
        setSpinnerListener(spinner, name)
    }

    private fun setSpinnerListener(spinner: Spinner, key: String) {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(adapterView: AdapterView<*>) {
            }

            override fun onItemSelected(adapterView: AdapterView<*>, view: View?, position: Int, id: Long) {
                SharedPreferenceUtil.set(userInfo1Context, key, adapterView.getItemAtPosition(position).toString())
            }
        }
    }

    private fun loadPage1Data(yearSpinner: Spinner, gradeSpinner: Spinner) {
        val selectedYear = SharedPreferenceUtil.get(userInfo1Context, STUDENT_YEAR, "18")
        val selectedGrade = SharedPreferenceUtil.get(userInfo1Context, STUDENT_GRADE, "1 학년")
        yearSpinner.setSelection(yearSpinnerArrayAdapter.getPosition(selectedYear))
        gradeSpinner.setSelection(gradeSpinnerArrayAdapter.getPosition(selectedGrade))
    }

    companion object {
        const val STUDENT_YEAR = "student_year"
        const val STUDENT_GRADE = "student_grade"
    }
}


