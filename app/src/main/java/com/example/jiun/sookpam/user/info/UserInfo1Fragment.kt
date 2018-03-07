package com.example.jiun.sookpam.user.info

import android.app.Activity
import android.support.v4.app.Fragment
import android.content.*
import android.os.Bundle
import android.support.v7.widget.*
import android.view.*
import android.widget.*
import com.example.jiun.sookpam.R
import com.example.jiun.sookpam.user.setting.UserSettingLibrary
import com.example.jiun.sookpam.user.major.*
import com.example.jiun.sookpam.util.SharedPreferenceUtil
import kotlinx.android.synthetic.main.fragment_user_info1.*

class UserInfo1Fragment : Fragment() {
    private var userInfo1View: View? = null
    private var userInfo1Activity: Activity? = null
    private var userInfo1Context: Context? = null
    private lateinit var studentYearSpinner: Spinner
    private lateinit var studentGradeSpinner: Spinner
    private lateinit var majorsRecyclerView: RecyclerView
    private lateinit var majorSelectingButton: Button
    private lateinit var yearSpinnerArrayAdapter: ArrayAdapter<String>
    private lateinit var gradeSpinnerArrayAdapter: ArrayAdapter<String>
    private lateinit var selectedMajors:ArrayList<String>

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
        UserSettingLibrary.setSpinnerAdapter(studentYearSpinner, yearSpinnerArrayAdapter, UserSettingLibrary.STUDENT_YEAR, userInfo1Context!!)
        UserSettingLibrary.setSpinnerAdapter(studentGradeSpinner, gradeSpinnerArrayAdapter, UserSettingLibrary.STUDENT_GRADE, userInfo1Context!!)
        majorSelectingButton = user_info1_major_btn
        majorSelectingButton.setOnClickListener {
            val intent = Intent(userInfo1Context, MajorActivity::class.java)
            selectedMajors = UserSettingLibrary.getSelectedMajors(context!!)
            intent.putExtra("selectedMajors", selectedMajors)
            startActivityForResult(intent, UserSettingLibrary.MAJOR_REQUEST_CODE)
        }
        majorsRecyclerView = user_info1_majors_recycler_view
        majorsRecyclerView.layoutManager = LinearLayoutManager(userInfo1Context)
        UserSettingLibrary.loadMajors(majorSelectingButton, majorsRecyclerView, context!!)
    }

    private fun loadPage1Data(yearSpinner: Spinner, gradeSpinner: Spinner) {
        val selectedYear = SharedPreferenceUtil.get(userInfo1Context, UserSettingLibrary.STUDENT_YEAR, "18")
        val selectedGrade = SharedPreferenceUtil.get(userInfo1Context, UserSettingLibrary.STUDENT_GRADE, "1 학년")
        yearSpinner.setSelection(yearSpinnerArrayAdapter.getPosition(selectedYear))
        gradeSpinner.setSelection(gradeSpinnerArrayAdapter.getPosition(selectedGrade))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            UserSettingLibrary.loadMajors(majorSelectingButton, majorsRecyclerView, context!!)
        }
    }
}

