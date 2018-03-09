package com.example.jiun.sookpam.user.setting

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.widget.Button
import android.widget.Toast
import com.example.jiun.sookpam.R
import com.example.jiun.sookpam.user.info.UserInfoActivity
import com.example.jiun.sookpam.util.SharedPreferenceUtil

class SettingCategory(val context: Context, private val pageNumber: Int = 0) {
    fun setCategoryButtonListener(button: Button, message: String) {
        val categoryName = button.text.toString()
        val currentKey = SharedPreferenceUtil.get(context, categoryName, NORMAL_CATEGORY)
        when (pageNumber) {
            PAGE3 -> when (currentKey) {
                NORMAL_CATEGORY -> {
                    SharedPreferenceUtil.set(context, categoryName, INTEREST_CATEGORY)
                    button.setBackgroundResource(R.drawable.circle_shape_blue)
                    button.setTextColor(Color.WHITE)
                    Toast.makeText(context, categoryName + " 항목이 관심 목록에 추가되었습니다.", Toast.LENGTH_SHORT).show()
                }
                INTEREST_CATEGORY -> {
                    SharedPreferenceUtil.set(context, categoryName, NORMAL_CATEGORY)
                    button.setBackgroundResource(R.drawable.circle_shape_white_blue)
                    button.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    Toast.makeText(context, categoryName + " 항목이 관심 목록에서 해제되었습니다.", Toast.LENGTH_SHORT).show()
                }
                UNINTEREST_CATEGORY -> {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            }
            PAGE4 -> when (currentKey) {
                NORMAL_CATEGORY -> {
                    SharedPreferenceUtil.set(context, categoryName, UNINTEREST_CATEGORY)
                    button.setBackgroundResource(R.drawable.circle_shape_dark_gray)
                    button.setTextColor(Color.WHITE)
                    Toast.makeText(context, categoryName + " 항목이 관심 없음 목록에 추가되었습니다.", Toast.LENGTH_SHORT).show()
                }
                UNINTEREST_CATEGORY -> {
                    SharedPreferenceUtil.set(context, categoryName, NORMAL_CATEGORY)
                    button.setBackgroundResource(R.drawable.circle_shape_white_dark_gray)
                    button.setTextColor(ContextCompat.getColor(context, R.color.colorDarkGray))
                    Toast.makeText(context, categoryName + " 항목이 관심 없음 목록에서 해제되었습니다.", Toast.LENGTH_SHORT).show()
                }
                INTEREST_CATEGORY -> {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            }
            else -> when (currentKey) {
                NORMAL_CATEGORY -> {
                    SharedPreferenceUtil.set(context, categoryName, INTEREST_CATEGORY)
                    button.setBackgroundResource(R.drawable.circle_shape_blue)
                    button.setTextColor(Color.WHITE)
                    Toast.makeText(context, categoryName + " 항목이 관심 목록에 추가되었습니다.", Toast.LENGTH_SHORT).show()
                }
                UNINTEREST_CATEGORY -> {
                    SharedPreferenceUtil.set(context, categoryName, NORMAL_CATEGORY)
                    button.setBackgroundResource(R.drawable.circle_shape_white_blue)
                    button.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    Toast.makeText(context, categoryName + " 항목이 기본 상태로 변경 되었습니다.", Toast.LENGTH_SHORT).show()
                }
                INTEREST_CATEGORY -> {
                    if (UserInfoActivity.countInterestCategories(context) > 3) {
                        SharedPreferenceUtil.set(context, categoryName, UNINTEREST_CATEGORY)
                        button.setBackgroundResource(R.drawable.circle_shape_dark_gray)
                        button.setTextColor(Color.WHITE)
                        Toast.makeText(context, categoryName + " 항목이 관심 없음 목록에 추가되었습니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    fun setColorsOf(detailButtons: ArrayList<Button>) {
        for (button in detailButtons) {
            setColorOf(button)
        }
    }

    private fun setColorOf(button: Button) {
        val categoryName = button.text.toString()
        val categoryStatus = SharedPreferenceUtil
                .get(context, categoryName, NORMAL_CATEGORY)
        if (pageNumber == PAGE3) {
            when (categoryStatus) {
                NORMAL_CATEGORY -> {
                    button.setBackgroundResource(R.drawable.circle_shape_white_blue)
                    button.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
                }
                INTEREST_CATEGORY -> {
                    button.setBackgroundResource(R.drawable.circle_shape_blue)
                    button.setTextColor(Color.WHITE)
                }
                UNINTEREST_CATEGORY -> {
                    button.setBackgroundResource(R.drawable.circle_shape_gray)
                    button.setTextColor(Color.WHITE)
                }
            }
        } else if (pageNumber == PAGE4) {
            when (categoryStatus) {
                NORMAL_CATEGORY -> {
                    button.setBackgroundResource(R.drawable.circle_shape_white_dark_gray)
                    button.setTextColor(ContextCompat.getColor(context, R.color.colorDarkGray))
                }
                INTEREST_CATEGORY -> {
                    button.setBackgroundResource(R.drawable.circle_shape_gray)
                    button.setTextColor(Color.WHITE)
                }
                UNINTEREST_CATEGORY -> {
                    button.setBackgroundResource(R.drawable.circle_shape_dark_gray)
                    button.setTextColor(Color.WHITE)
                }
            }
        } else {
            when (categoryStatus) {
                NORMAL_CATEGORY -> {
                    button.setBackgroundResource(R.drawable.circle_shape_white_blue)
                    button.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
                }
                INTEREST_CATEGORY -> {
                    button.setBackgroundResource(R.drawable.circle_shape_blue)
                    button.setTextColor(Color.WHITE)
                }
                UNINTEREST_CATEGORY -> {
                    button.setBackgroundResource(R.drawable.circle_shape_dark_gray)
                    button.setTextColor(Color.WHITE)
                }
            }
        }
    }

    companion object {
        const val NORMAL_CATEGORY = 0
        const val INTEREST_CATEGORY = 1
        const val UNINTEREST_CATEGORY = 2
        const val PAGE3 = 2
        const val PAGE4 = 3
        val categories = listOf("장학", "학사", "행사", "모집", "시스템", "국제", "취업", "학생")
    }
}
