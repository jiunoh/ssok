package com.example.jiun.sookpam.user

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import com.example.jiun.sookpam.R
import kotlinx.android.synthetic.main.activity_user_info.*

class UserInfoActivity : AppCompatActivity() {
    private val viewPager by lazy { user_view_pager }
    private var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        val adapter = SimpleFragmentPagerAdapter(supportFragmentManager, MAX_PAGE_SIZE)
        viewPager.apply {
            this.adapter = adapter
            currentFragment = adapter.getItem(SimpleFragmentPagerAdapter.USER_INFO_1)
        }

        fun getViewPager(): ViewPager {
            return viewPager
        }
    }

    companion object {
        const val MAX_PAGE_SIZE = 4
    }
}
