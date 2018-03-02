package com.example.jiun.sookpam.user

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.widget.*
import com.example.jiun.sookpam.R
import com.example.jiun.sookpam.util.SharedPreferenceUtil

class PersonalCategory(val context: Context, private val pageNumber: Int = 0) {
    fun setCategoryButtonListener(button: Button, message: String) {
        val categoryName = button.text.toString()
        val currentKey = SharedPreferenceUtil.get(context, categoryName, PersonalCategory.NORMAL_CATEGORY)
        when (pageNumber) {
            PAGE3 -> when (currentKey) {
                PersonalCategory.NORMAL_CATEGORY -> {
                    SharedPreferenceUtil.set(context, categoryName, PersonalCategory.INTEREST_CATEGORY)
                    button.setBackgroundResource(R.drawable.circle_shape_blue)
                    button.setTextColor(Color.WHITE)
                    Toast.makeText(context, categoryName + " 카테고리가 관심 목록에 추가되었습니다.", Toast.LENGTH_SHORT).show()
                }
                PersonalCategory.INTEREST_CATEGORY -> {
                    SharedPreferenceUtil.set(context, categoryName, PersonalCategory.NORMAL_CATEGORY)
                    button.setBackgroundResource(R.drawable.circle_shape_white_blue)
                    button.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    Toast.makeText(context, categoryName + " 카테고리가 관심 목록에서 해제되었습니다.", Toast.LENGTH_SHORT).show()
                }
                PersonalCategory.UNINTEREST_CATEGORY -> {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            }
            PAGE4 -> when (currentKey) {
                PersonalCategory.NORMAL_CATEGORY -> {
                    SharedPreferenceUtil.set(context, categoryName, PersonalCategory.UNINTEREST_CATEGORY)
                    button.setBackgroundResource(R.drawable.circle_shape_dark_gray)
                    button.setTextColor(Color.WHITE)
                    Toast.makeText(context, categoryName + " 카테고리가 관심 없음 목록에 추가되었습니다.", Toast.LENGTH_SHORT).show()
                }
                PersonalCategory.UNINTEREST_CATEGORY -> {
                    SharedPreferenceUtil.set(context, categoryName, PersonalCategory.NORMAL_CATEGORY)
                    button.setBackgroundResource(R.drawable.circle_shape_white_dark_gray)
                    button.setTextColor(ContextCompat.getColor(context, R.color.colorDarkGray))
                    Toast.makeText(context, categoryName + " 카테고리가 관심 없음 목록에서 해제되었습니다.", Toast.LENGTH_SHORT).show()
                }
                PersonalCategory.INTEREST_CATEGORY -> {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            }
            else -> when (currentKey) {
                PersonalCategory.NORMAL_CATEGORY -> {
                    SharedPreferenceUtil.set(context, categoryName, PersonalCategory.INTEREST_CATEGORY)
                    button.setBackgroundResource(R.drawable.circle_shape_blue)
                    button.setTextColor(Color.WHITE)
                    Toast.makeText(context, categoryName + " 카테고리가 관심 목록에 추가되었습니다.", Toast.LENGTH_SHORT).show()
                }
                PersonalCategory.UNINTEREST_CATEGORY -> {
                    SharedPreferenceUtil.set(context, categoryName, PersonalCategory.NORMAL_CATEGORY)
                    button.setBackgroundResource(R.drawable.circle_shape_white_blue)
                    button.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    Toast.makeText(context, categoryName + " 카테고리가 기본 상태로 변경 되었습니다.", Toast.LENGTH_SHORT).show()
                }
                PersonalCategory.INTEREST_CATEGORY -> {
                    if (UserInfoActivity.countInterestCategories(context) > 3) {
                        SharedPreferenceUtil.set(context, categoryName, PersonalCategory.UNINTEREST_CATEGORY)
                        button.setBackgroundResource(R.drawable.circle_shape_dark_gray)
                        button.setTextColor(Color.WHITE)
                        Toast.makeText(context, categoryName + " 카테고리가 관심 없음 목록에 추가되었습니다.", Toast.LENGTH_SHORT).show()
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
                .get(context, categoryName, PersonalCategory.NORMAL_CATEGORY)
        if (pageNumber == PAGE3) {
            when (categoryStatus) {
                PersonalCategory.NORMAL_CATEGORY -> {
                    button.setBackgroundResource(R.drawable.circle_shape_white_blue)
                    button.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
                }
                PersonalCategory.INTEREST_CATEGORY -> {
                    button.setBackgroundResource(R.drawable.circle_shape_blue)
                    button.setTextColor(Color.WHITE)
                }
                PersonalCategory.UNINTEREST_CATEGORY -> {
                    button.setBackgroundResource(R.drawable.circle_shape_gray)
                    button.setTextColor(Color.WHITE)
                }
            }
        } else if (pageNumber == PAGE4) {
            when (categoryStatus) {
                PersonalCategory.NORMAL_CATEGORY -> {
                    button.setBackgroundResource(R.drawable.circle_shape_white_dark_gray)
                    button.setTextColor(ContextCompat.getColor(context, R.color.colorDarkGray))
                }
                PersonalCategory.INTEREST_CATEGORY -> {
                    button.setBackgroundResource(R.drawable.circle_shape_gray)
                    button.setTextColor(Color.WHITE)
                }
                PersonalCategory.UNINTEREST_CATEGORY -> {
                    button.setBackgroundResource(R.drawable.circle_shape_dark_gray)
                    button.setTextColor(Color.WHITE)
                }
            }
        } else {
            when (categoryStatus) {
                PersonalCategory.NORMAL_CATEGORY -> {
                    button.setBackgroundResource(R.drawable.circle_shape_white_blue)
                    button.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
                }
                PersonalCategory.INTEREST_CATEGORY -> {
                    button.setBackgroundResource(R.drawable.circle_shape_blue)
                    button.setTextColor(Color.WHITE)
                }
                PersonalCategory.UNINTEREST_CATEGORY -> {
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
        val categories = listOf("장학", "학사", "입학", "모집", "시스템", "국제", "취업", "학생")
    }
}
