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
    private var userInfo4View: View? = null
    private var userInfo4Activity: Activity? = null
    private var userInfo4Context: Context? = null
    private var personalCategory:PersonalCategory?=null
    private var detailButtons: ArrayList<Button> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        userInfo4View = inflater.inflate(R.layout.fragment_user_info4, container, false)
        userInfo4Activity = activity
        userInfo4Context = userInfo4View!!.context
        personalCategory = PersonalCategory(userInfo4Context!!, PersonalCategory.PAGE4)
        return userInfo4View
    }

    override fun onStart() {
        super.onStart()
        initializeDetailButtons()
    }

    override fun onResume() {
        super.onResume()
        personalCategory!!.setColorsOf(detailButtons)
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
                personalCategory!!.setCategoryButtonListener(button
                        , getString(R.string.user_info4_interest_already_checked))
            }
        }
    }
}
