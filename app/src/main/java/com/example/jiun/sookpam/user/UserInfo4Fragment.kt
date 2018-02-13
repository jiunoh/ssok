package com.example.jiun.sookpam.user

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.example.jiun.sookpam.R
import kotlinx.android.synthetic.main.fragment_user_info4.*


class UserInfo4Fragment : Fragment() {
    private lateinit var userInfo4View: View
    private var userInfo4Activity: Activity? = null
    private lateinit var userInfo4Context: Context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        userInfo4View = inflater.inflate(R.layout.fragment_user_info4, container, false)
        userInfo4Activity = activity
        userInfo4Context = userInfo4View.context
        return userInfo4View
    }
}
