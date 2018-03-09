package com.example.jiun.sookpam.user

import android.content.Context
import com.example.jiun.sookpam.user.setting.UserSettingLibrary
import com.example.jiun.sookpam.util.SharedPreferenceUtil

data class UserInformation(
        val context: Context,
        val studentGrade: String = SharedPreferenceUtil.get(context, UserSettingLibrary.STUDENT_GRADE, "1 학년"),
        val studentYear: String = SharedPreferenceUtil.get(context,
                UserSettingLibrary.STUDENT_YEAR, "18"),
        val major1: String = UserSettingLibrary.getSelectedMajors(context)[0],
        val major2: String =
                if (UserSettingLibrary.getSelectedMajors(context).size > 1) UserSettingLibrary.getSelectedMajors(context)[1] else "null",
        val schoolScholar: Boolean = SharedPreferenceUtil.get(context, "교내 장학금", false),
        val governmentScholar: Boolean = SharedPreferenceUtil.get(context, "국가 장학금", false),
        val externalScholar: Boolean = SharedPreferenceUtil.get(context, "교외 장학금", false),
        val studentStatus: Boolean = SharedPreferenceUtil.get(context, UserSettingLibrary.STUDENT_STATUS, true),
        val interestScholarship: Int = SharedPreferenceUtil.get(context, "장학", 0),
        val interestAcademic: Int = SharedPreferenceUtil.get(context, "학사", 0),
        val interestEvent: Int = SharedPreferenceUtil.get(context, "행사", 0),
        val interestRecruit: Int = SharedPreferenceUtil.get(context, "모집", 0),
        val interestSystem: Int = SharedPreferenceUtil.get(context, "시스템", 0),
        val interestGlobal: Int = SharedPreferenceUtil.get(context, "국제", 0),
        val interestCareer: Int = SharedPreferenceUtil.get(context, "취업", 0),
        val interestStudent: Int = SharedPreferenceUtil.get(context, "학생", 0)
)
