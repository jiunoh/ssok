package com.example.jiun.sookpam.user

import android.app.Activity
import android.support.v4.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        userInfo1View = inflater.inflate(R.layout.fragment_user_info1, container, false)
        userInfo1Activity = activity
        userInfo1Context = userInfo1View!!.context
        return userInfo1View
    }

    override fun onStart() {
        super.onStart()
        initialize()
    }

    private fun initialize() {
        studentYearSpinner = user_info1_student_year_spinner
        studentGradeSpinner = user_info1_student_grade_spinner
        majorSelectingButton = user_info1_major_btn
        setSpinnerListener(studentYearSpinner, STUDENT_YEAR)
        setSpinnerListener(studentGradeSpinner, STUDENT_GRADE)
    }

    private fun setSpinnerListener(spinner: Spinner, key: String) {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(adapterView: AdapterView<*>) {
                SharedPreferenceUtil.set(userInfo1Context, key, adapterView.getItemAtPosition(DEFAULT_POSITION).toString())
            }

            override fun onItemSelected(adapterView: AdapterView<*>, view: View?, position: Int, id: Long) {
                SharedPreferenceUtil.set(userInfo1Context, key, adapterView.getItemAtPosition(position).toString())
            }
        }
    }

    companion object {
        const val STUDENT_YEAR = "student_year"
        const val STUDENT_GRADE = "student_grade"
        const val DEFAULT_POSITION = 0
    }
}


