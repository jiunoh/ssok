package com.example.jiun.sookpam.user.setting

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.widget.Button
import com.example.jiun.sookpam.CustomToast
import com.example.jiun.sookpam.R
import com.example.jiun.sookpam.user.info.UserInfoActivity
import com.example.jiun.sookpam.util.SharedPreferenceUtil

class SettingCategory(val context: Context) {
    companion object {
        private const val NORMAL_CATEGORY = 0
        private const val INTEREST_CATEGORY = 1
        val categories = listOf("장학", "학사", "행사", "모집", "시스템", "국제", "취업", "학생")

        fun countInterestCategories(context: Context): Int {
            return SettingCategory.categories.count { getCategoryStatus(it, context) == SettingCategory.INTEREST_CATEGORY }
        }

        private fun getCategoryStatus(key: String, context: Context): Int {
            return SharedPreferenceUtil.get(context, key, SettingCategory.NORMAL_CATEGORY)
        }
    }
}
