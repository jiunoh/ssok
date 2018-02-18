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
import kotlinx.android.synthetic.main.fragment_user_info4.*


class UserInfo4Fragment : Fragment() {
    private lateinit var userInfo4View: View
    private var userInfo4Activity: Activity? = null
    private lateinit var userInfo4Context: Context
    private var detailButtons: ArrayList<Button> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        userInfo4View = inflater.inflate(R.layout.fragment_user_info4, container, false)
        userInfo4Activity = activity
        userInfo4Context = userInfo4View.context
        return userInfo4View
    }


    override fun onStart() {
        super.onStart()
        initializeDetailButtons()
    }

    private fun initializeDetailButtons() {
        detailButtons.add(user_info4_scholarship_btn)
        detailButtons.add(user_info4_academic_btn)
        detailButtons.add(user_info4_entrance_btn)
        detailButtons.add(user_info4_recruit_btn)
        detailButtons.add(user_info4_it_btn)
        detailButtons.add(user_info4_global_btn)
        detailButtons.add(user_info4_career_btn)

        for (button in detailButtons) {
            button.setOnClickListener {
                setCategoryButtonListener(button)
            }
        }
    }

    private fun changeButtonColor(button: Button) {
        if (button.currentTextColor
                == ContextCompat.getColor(userInfo4Context, R.color.colorDarkGray)) {
            button.setBackgroundResource(R.drawable.circle_shape_dark_gray)
            button.setTextColor(Color.WHITE)
        } else {
            button.setBackgroundResource(R.drawable.circle_shape_white_dark_gray)
            button.setTextColor(ContextCompat.getColor(userInfo4Context, R.color.colorDarkGray))
        }
    }

    private fun setCategoryButtonListener(button: Button) {
        val categoryName = button.text.toString()
        val currentKey = SharedPreferenceUtil.get(userInfo4Context, categoryName, PersonalCategory.NORMAL_CATEGORY)
        when (currentKey) {
            PersonalCategory.NORMAL_CATEGORY -> {
                SharedPreferenceUtil.set(userInfo4Context, categoryName, PersonalCategory.UNINTEREST_CATEGORY)
                changeButtonColor(button)
            }
            PersonalCategory.UNINTEREST_CATEGORY -> {
                SharedPreferenceUtil.set(userInfo4Context, categoryName, PersonalCategory.NORMAL_CATEGORY)
                changeButtonColor(button)
            }
            PersonalCategory.INTEREST_CATEGORY -> {
                Toast.makeText(userInfo4Context, "해당 카테고리는 관심있는 카테고리로 설정되어 있습니다. 관심을 먼저 해제해주세요."
                        , Toast.LENGTH_SHORT).show()
            }
        }
    }
}
