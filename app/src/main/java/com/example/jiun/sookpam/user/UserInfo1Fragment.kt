package com.example.jiun.sookpam.user

import android.app.Activity
import android.support.v4.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.*
import com.example.jiun.sookpam.R
import kotlinx.android.synthetic.main.fragment_user_info1.*

class UserInfo1Fragment : Fragment() {
    private lateinit var userInfo1View: View
    private var userInfo1Activity: Activity? = null
    private lateinit var userInfo1Context: Context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        userInfo1View = inflater.inflate(R.layout.fragment_user_info1, container, false)
        userInfo1Activity = activity
        userInfo1Context = userInfo1View.context
        return userInfo1View
    }
}

