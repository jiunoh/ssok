package com.example.jiun.sookpam.user

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import kotlinx.android.synthetic.main.activity_user_info.*

class UserInfoActivity : AppCompatActivity() {
    private val viewPager by lazy { user_view_pager }
    val currentFragment = Fragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val adapter = SimpleFragmentPagerAdapter(supportFragmentManager, MAX_PAGE_SIZE)
        viewPager.apply {
            setBackgroundColor(Color.WHITE)
            this.adapter = adapter
        }

        fun getViewPager(): ViewPager {
            return viewPager
        }
    }

    companion object {
        val MAX_PAGE_SIZE = 4
    }
}
