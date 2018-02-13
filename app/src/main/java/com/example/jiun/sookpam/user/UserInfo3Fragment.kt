package com.example.jiun.sookpam.user

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.example.jiun.sookpam.R
import kotlinx.android.synthetic.main.fragment_user_info3.*


class UserInfo3Fragment  : Fragment() {
    private lateinit var userInfo3View: View
    private var userInfo3Activity: Activity? = null
    private lateinit var userInfo3Context: Context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        userInfo3View = inflater.inflate(R.layout.fragment_user_info3, container, false)
        userInfo3Activity = activity
        userInfo3Context = userInfo3View.context
        return userInfo3View
    }
}
