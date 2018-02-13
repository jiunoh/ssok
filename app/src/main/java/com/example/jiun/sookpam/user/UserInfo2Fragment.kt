package com.example.jiun.sookpam.user

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.example.jiun.sookpam.R
import kotlinx.android.synthetic.main.fragment_user_info2.*


class UserInfo2Fragment : Fragment() {
    private lateinit var userInfo2View: View
    private var userInfo2Activity: Activity? = null
    private lateinit var userInfo2Context: Context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        userInfo2View = inflater.inflate(R.layout.fragment_user_info2, container, false)
        userInfo2Activity = activity
        userInfo2Context = userInfo2View.context
        return userInfo2View
    }
}
