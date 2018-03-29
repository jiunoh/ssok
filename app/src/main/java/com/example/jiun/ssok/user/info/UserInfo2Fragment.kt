package com.example.jiun.ssok.user.info

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.*
import com.example.jiun.ssok.R
import com.example.jiun.ssok.user.setting.UserSettingLibrary
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
        UserSettingLibrary.setCheckBoxListener(schoolScholarShipCheckBox, userInfo2Context!!)
        UserSettingLibrary.setCheckBoxListener(suburbanScholarShipCheckBox, userInfo2Context!!)
        UserSettingLibrary.setCheckBoxListener(governmentScholarShipCheckBox, userInfo2Context!!)
        UserSettingLibrary.setRadioButtonListener(studentStatusRadioGroup, statusInRadioButton, statusOutRadioButton, userInfo2Context!!)
    }

    private fun loadPage2Data() {
        UserSettingLibrary.loadCheckBoxData(schoolScholarShipCheckBox, context!!)
        UserSettingLibrary.loadCheckBoxData(suburbanScholarShipCheckBox, context!!)
        UserSettingLibrary.loadCheckBoxData(governmentScholarShipCheckBox, context!!)
        UserSettingLibrary.loadRadioGroupData(studentStatusRadioGroup, statusInRadioButton, statusOutRadioButton, context!!)
    }
}
