package com.example.jiun.sookpam.user.setting

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.*
import com.example.jiun.sookpam.R
import com.example.jiun.sookpam.user.major.MajorList
import com.example.jiun.sookpam.util.SharedPreferenceUtil

class UserSettingLibrary {
    companion object {
        const val STUDENT_YEAR = "student_year"
        const val STUDENT_GRADE = "student_grade"
        const val STUDENT_STATUS = "student_status"
        const val MAJOR_REQUEST_CODE = 0
        private const val UNCHECKED = false
        private const val CHECKED = true

        fun setSpinnerAdapter(spinner: Spinner, spinnerArrayAdapter: ArrayAdapter<String>, name: String, context: Context) {
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
            spinner.adapter = spinnerArrayAdapter
            setSpinnerListener(spinner, name, context)
        }

        private fun setSpinnerListener(spinner: Spinner, key: String, context: Context) {
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(adapterView: AdapterView<*>) {
                }

                override fun onItemSelected(adapterView: AdapterView<*>, view: View?, position: Int, id: Long) {
                    SharedPreferenceUtil.set(context, key, adapterView.getItemAtPosition(position).toString())
                }
            }
        }

        fun loadMajors(majorSelectingButton: ImageButton, majorsRecyclerView: RecyclerView, context: Context) {
            val selectedMajors = getSelectedMajors(context)
            if (selectedMajors.size > 0) {
                majorSelectingButton.setImageResource(R.drawable.ic_edit_major)
            } else {
                majorSelectingButton.setImageResource(R.drawable.ic_add_major)
            }
            majorsRecyclerView.adapter = SelectedMajorRecyclerAdapter(selectedMajors)
        }

        fun getSelectedMajors(context: Context): ArrayList<String> {
            val selectedMajors = ArrayList<String>()
            for (college in MajorList.collegeAndMajors) {
                for (major in college) {
                    val doesMajorSelected = SharedPreferenceUtil.get(context, major, false)
                    if (doesMajorSelected && major !in selectedMajors) {
                        selectedMajors.add(major)
                    }
                }
            }
            return selectedMajors
        }

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
                        SharedPreferenceUtil.set(context, STUDENT_STATUS, true)
                    }
                    radioButton2.id -> {
                        SharedPreferenceUtil.set(context, STUDENT_STATUS, false)
                    }
                }
            }
        }

        fun loadCheckBoxData(checkBox: CheckBox, context: Context) {
            checkBox.isChecked = SharedPreferenceUtil.get(context, checkBox.text.toString(), false)
        }

        fun loadRadioGroupData(radioGroup: RadioGroup, radioButton1: RadioButton, radioButton2: RadioButton, context: Context) {
            val selectedRadioButton = SharedPreferenceUtil.get(context, STUDENT_STATUS, true)
            if (selectedRadioButton) {
                radioGroup.check(radioButton1.id)
            } else {
                radioGroup.check(radioButton2.id)
            }
        }
    }
}