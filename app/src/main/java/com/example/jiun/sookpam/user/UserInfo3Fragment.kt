package com.example.jiun.sookpam.user

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.*
import android.widget.Button
import com.example.jiun.sookpam.R
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
                changeButtonColor(button)
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
}
