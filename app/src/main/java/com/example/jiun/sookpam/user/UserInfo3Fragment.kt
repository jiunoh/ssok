package com.example.jiun.sookpam.user

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.*
import android.widget.Button
import android.widget.Toast
import com.example.jiun.sookpam.R
import com.example.jiun.sookpam.util.SharedPreferenceUtil
import kotlinx.android.synthetic.main.fragment_user_info3.*


class UserInfo3Fragment : Fragment() {
    private lateinit var userInfo3View: View
    private var detailButtons: ArrayList<Button> = ArrayList()
    private var userInfo3Activity: Activity? = null
    private lateinit var userInfo3Context: Context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        userInfo3View = inflater.inflate(R.layout.fragment_user_info3, container, false)
        userInfo3Activity = activity
        userInfo3Context = userInfo3View.context
        return userInfo3View
    }

    override fun onStart() {
        super.onStart()
        initializeDetailButtons()
    }

    private fun initializeDetailButtons() {
        detailButtons.add(user_info3_scholarship_btn)
        detailButtons.add(user_info3_academic_btn)
        detailButtons.add(user_info3_entrance_btn)
        detailButtons.add(user_info3_recruit_btn)
        detailButtons.add(user_info3_it_btn)
        detailButtons.add(user_info3_global_btn)
        detailButtons.add(user_info3_career_btn)

        for (button in detailButtons) {
            button.setOnClickListener {
                setCategoryButtonListener(button)
            }
            getColorSet(button)
        }
    }

    private fun setCategoryButtonListener(button: Button) {
        val categoryName = button.text.toString()
        val currentKey = SharedPreferenceUtil.get(userInfo3Context, categoryName, PersonalCategory.NORMAL_CATEGORY)
        when(currentKey) {
            PersonalCategory.NORMAL_CATEGORY -> {
                SharedPreferenceUtil.set(userInfo3Context, categoryName, PersonalCategory.INTEREST_CATEGORY)
                changeButtonColor(button)
            }
            PersonalCategory.INTEREST_CATEGORY -> {
                SharedPreferenceUtil.set(userInfo3Context, categoryName, PersonalCategory.NORMAL_CATEGORY)
                changeButtonColor(button)
            }
            PersonalCategory.UNINTEREST_CATEGORY -> {
                Toast.makeText(userInfo3Context, getString(R.string.user_info3_uninterest_already_checked)
                        , Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun changeButtonColor(button: Button) {
        if (button.currentTextColor
                == ContextCompat.getColor(userInfo3Context, R.color.colorPrimary)) {
            button.setBackgroundResource(R.drawable.circle_shape_blue)
            button.setTextColor(Color.WHITE)
        } else {
            button.setBackgroundResource(R.drawable.circle_shape_white_blue)
            button.setTextColor(ContextCompat.getColor(userInfo3Context, R.color.colorPrimary))
        }
    }

    private fun getColorSet(button: Button) {
        val categoryName = button.text.toString()
        val categoryStatus = SharedPreferenceUtil
                .get(userInfo3Context, categoryName, PersonalCategory.NORMAL_CATEGORY)
        when(categoryStatus) {
            PersonalCategory.NORMAL_CATEGORY -> {
                button.setBackgroundResource(R.drawable.circle_shape_white_blue)
                button.setTextColor(ContextCompat.getColor(userInfo3Context, R.color.colorPrimary))
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
    }
}
