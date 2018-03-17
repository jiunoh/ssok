package com.example.jiun.sookpam.user.setting

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.widget.Button
import com.example.jiun.sookpam.CustomToast
import com.example.jiun.sookpam.R
import com.example.jiun.sookpam.user.info.UserInfoActivity
import com.example.jiun.sookpam.util.SharedPreferenceUtil

class SettingCategory(val context: Context, private val pageNumber: Int = 0) {
    companion object {
        const val NORMAL_CATEGORY = 0
        const val INTEREST_CATEGORY = 1
        const val UNINTEREST_CATEGORY = 2
        const val PAGE3 = 2
        val categories = listOf("장학", "학사", "행사", "모집", "시스템", "국제", "취업", "학생")
    }
}
