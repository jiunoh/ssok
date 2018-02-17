package com.example.jiun.sookpam.user

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.example.jiun.sookpam.R
import kotlinx.android.synthetic.main.activity_user_info.*

class UserInfoActivity : AppCompatActivity() {
    private lateinit var viewPager: CustomViewPager
    private lateinit var currentFragment: Fragment
    private lateinit var previousButton: Button
    private lateinit var nextButton: Button
    private var circleImageViewArrayList: ArrayList<ImageView> = ArrayList(3)
    private var pagerAdapter = SimpleFragmentPagerAdapter(supportFragmentManager, MAX_PAGE_SIZE)
    private var currentPage = SimpleFragmentPagerAdapter.USER_INFO_1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
        initialize()
        movePage()
    }

    private fun initialize() {
        initializeViewPager()
        initializeMoveButtons()
        initializeCircleImages()
    }

    private fun initializeViewPager() {
        viewPager = user_view_pager
        viewPager.apply {
            this.setPagingEnabled(false)
            this.adapter = pagerAdapter
            currentFragment = pagerAdapter.getItem(currentPage)
        }
    }

    private fun initializeMoveButtons() {
        previousButton = user_info_previous_btn
        nextButton = user_info_next_btn
    }

    private fun initializeCircleImages() {
        (0..3)
                .map {
                    resources.getIdentifier("user_info_circle" + it + "_img", "id"
                            , packageName)
                }
                .forEach { circleImageViewArrayList.add(findViewById(it)) }
    }

    private fun movePage() {
        movePrevious()
        moveNext()
    }

    private fun movePrevious() {
        previousButton.setOnClickListener {
            if (currentPage == SimpleFragmentPagerAdapter.USER_INFO_4) {
                nextButton.text = getText(R.string.user_info_next_page)
            }
            if (currentPage > SimpleFragmentPagerAdapter.USER_INFO_1) {
                if (currentPage == SimpleFragmentPagerAdapter.USER_INFO_2) {
                    previousButton.visibility = View.INVISIBLE
                }
                currentPage -= 1
                viewPager.setCurrentItem(currentPage, true)
                changeCircleColor(MOVE_PREVIOUS_PAGE)
            }
        }
    }

    private fun moveNext() {
        nextButton.setOnClickListener {
            if (currentPage == SimpleFragmentPagerAdapter.USER_INFO_3) {
                nextButton.text = getText(R.string.user_info_done)
            }
            if (currentPage < SimpleFragmentPagerAdapter.USER_INFO_4) {
                if (currentPage == SimpleFragmentPagerAdapter.USER_INFO_1) {
                    previousButton.visibility = View.VISIBLE
                }
                currentPage += 1
                viewPager.setCurrentItem(currentPage, true)
                changeCircleColor(MOVE_NEXT_PAGE)
            }
        }
    }

    private fun changeCircleColor(doesMovePrevious: Boolean) {
        val previousPage: Int =
                if (doesMovePrevious) {
                    currentPage + 1
                } else {
                    currentPage - 1
                }
        circleImageViewArrayList[previousPage].setImageResource(R.drawable.ic_default_circle)
        circleImageViewArrayList[currentPage].setImageResource(R.drawable.ic_pink_circle)
    }

    fun getViewPager(): ViewPager {
        return viewPager
    }

    companion object {
        const val MAX_PAGE_SIZE = 4
        const val MOVE_PREVIOUS_PAGE = true
        const val MOVE_NEXT_PAGE = false
    }
}
