package com.example.jiun.sookpam.user

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.*
import com.example.jiun.sookpam.R
import com.example.jiun.sookpam.util.SharedPreferenceUtil
import kotlinx.android.synthetic.main.fragment_user_info2.*


class UserInfo2Fragment : Fragment() {
    private var userInfo2View: View? = null
    private var userInfo2Activity: Activity? = null
    private var userInfo2Context: Context? = null
    private lateinit var schoolScholarShipCheckBox: CheckBox
    private lateinit var suburbanScholarShipCheckBox: CheckBox
    private lateinit var governmentScholarShipCheckBox: CheckBox
    private lateinit var studentStatusRadioGroup: RadioGroup
    private lateinit var statusInRadioButton: RadioButton
    private lateinit var statusOutRadioButton: RadioButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        userInfo2View = inflater.inflate(R.layout.fragment_user_info2, container, false)
        userInfo2Activity = activity
        userInfo2Context = userInfo2View!!.context
        return userInfo2View
    }

    override fun onStart() {
        super.onStart()
        initialize()
    }

    override fun onResume() {
        super.onResume()
        loadPage2Data()
    }

    private fun initialize() {
        schoolScholarShipCheckBox = user_info2_school_scholarship_checkbox
        suburbanScholarShipCheckBox = user_info2_suburban_scholarship_checkbox
        governmentScholarShipCheckBox = user_info2_government_scholarship_checkbox
        studentStatusRadioGroup = user_info2_status_radio_group
        statusInRadioButton = user_info2_status_in_radio_btn
        statusOutRadioButton = user_info2_status_out_radio_btn
        setCheckBoxListener(schoolScholarShipCheckBox, userInfo2Context!!)
        setCheckBoxListener(suburbanScholarShipCheckBox, userInfo2Context!!)
        setCheckBoxListener(governmentScholarShipCheckBox, userInfo2Context!!)
        setRadioButtonListener(studentStatusRadioGroup, statusInRadioButton, statusOutRadioButton, userInfo2Context!!)
    }

    private fun loadPage2Data() {
        loadCheckBoxData(schoolScholarShipCheckBox, context!!)
        loadCheckBoxData(suburbanScholarShipCheckBox, context!!)
        loadCheckBoxData(governmentScholarShipCheckBox, context!!)
        loadRadioGroupData(studentStatusRadioGroup, statusInRadioButton, statusOutRadioButton, context!!)
    }

    companion object {
        private const val UNCHECKED = false
        private const val CHECKED = true
        const val STUDENT_STATUS = "student_status"

        fun setCheckBoxListener(checkBox: CheckBox, context: Context) {
            checkBox.setOnClickListener {
                if (checkBox.isChecked) {
                    SharedPreferenceUtil.set(context, checkBox.text.toString(), CHECKED)
                } else {
                    SharedPreferenceUtil.set(context, checkBox.text.toString(), UNCHECKED)
                }
            }
        }

        fun setRadioButtonListener(radioGroup: RadioGroup, radioButton1: RadioButton, radioButton2: RadioButton, context: Context) {
            radioGroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    radioButton1.id -> {
                        SharedPreferenceUtil.set(context, STUDENT_STATUS, radioButton1.text.toString())
                    }
                    radioButton2.id -> {
                        SharedPreferenceUtil.set(context, STUDENT_STATUS, radioButton2.text.toString())
                    }
                }
            }
        }

        fun loadCheckBoxData(checkBox: CheckBox, context: Context) {
            checkBox.isChecked = SharedPreferenceUtil.get(context, checkBox.text.toString(), false)
        }

        fun loadRadioGroupData(radioGroup: RadioGroup, radioButton1: RadioButton, radioButton2: RadioButton, context: Context) {
            val selectedRadioButton = SharedPreferenceUtil.get(context, STUDENT_STATUS, radioButton1.text.toString())
            if (selectedRadioButton == radioButton1.text.toString()) {
                radioGroup.check(radioButton1.id)
            } else {
                radioGroup.check(radioButton2.id)
            }
        }
    }
}
