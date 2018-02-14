package com.example.jiun.sookpam.user

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.Button
import com.example.jiun.sookpam.R
import kotlinx.android.synthetic.main.activity_user_info.*

class UserInfoActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager
    private lateinit var currentFragment: Fragment
    private lateinit var previousButton: Button
    private lateinit var nextButton: Button
    private var pagerAdapter = SimpleFragmentPagerAdapter(supportFragmentManager, MAX_PAGE_SIZE)
    private var currentPage = SimpleFragmentPagerAdapter.USER_INFO_1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
        initialize()
        movePage()
    }

    private fun initialize() {
        viewPager = user_view_pager
        viewPager.apply {
            this.adapter = pagerAdapter
            currentFragment = pagerAdapter.getItem(currentPage)
        }
        previousButton = user_info_previous_btn
        nextButton = user_info_next_btn
    }

    private fun movePage() {
        previousButton.setOnClickListener {
            if (currentPage == SimpleFragmentPagerAdapter.USER_INFO_2) {
                previousButton.visibility = View.INVISIBLE
            }
            if (currentPage > SimpleFragmentPagerAdapter.USER_INFO_1) {
                currentPage -= 1
                viewPager.setCurrentItem(currentPage, true)
            }
        }

        nextButton.setOnClickListener {
            if (currentPage == SimpleFragmentPagerAdapter.USER_INFO_3) {
                nextButton.text = "완료"
            }
            if (currentPage < SimpleFragmentPagerAdapter.USER_INFO_4) {
                if (currentPage == SimpleFragmentPagerAdapter.USER_INFO_1) {
                    previousButton.visibility = View.VISIBLE
                }
                currentPage += 1
                viewPager.setCurrentItem(currentPage, true)
            }
        }
    }

    fun getViewPager(): ViewPager {
        return viewPager
    }

    companion object {
        const val MAX_PAGE_SIZE = 4
    }
}
