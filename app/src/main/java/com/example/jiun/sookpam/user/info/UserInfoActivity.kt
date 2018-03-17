package com.example.jiun.sookpam.user.info

import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.View
import android.widget.*
import com.example.jiun.sookpam.R
import com.example.jiun.sookpam.SimpleViewPager
import com.example.jiun.sookpam.user.setting.SettingCategory
import com.example.jiun.sookpam.user.major.MajorList
import com.example.jiun.sookpam.user.setting.UserSettingLibrary
import com.example.jiun.sookpam.util.SharedPreferenceUtil
import kotlinx.android.synthetic.main.activity_user_info.*

class UserInfoActivity : AppCompatActivity() {
    private lateinit var viewPager: SimpleViewPager
    private var currentFragment: Fragment? = null
    private lateinit var previousButton: Button
    private lateinit var nextButton: Button
    private var circleImageViewArrayList: ArrayList<ImageView> = ArrayList(3)
    private var pagerAdapter = UserInfoFragmentPagerAdapter(supportFragmentManager, MAX_PAGE_SIZE)
    private var currentPage = UserInfoFragmentPagerAdapter.USER_INFO_1
    private var isUserDoubleClickBackPressed: Boolean = false


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
            this.offscreenPageLimit = 3
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
            if (currentPage == UserInfoFragmentPagerAdapter.USER_INFO_4) {
                nextButton.text = getText(R.string.user_info_next_page)
                pagerAdapter.notifyDataSetChanged()
            }
            if (currentPage > UserInfoFragmentPagerAdapter.USER_INFO_1) {
                if (currentPage == UserInfoFragmentPagerAdapter.USER_INFO_2) {
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
            if (currentPage < UserInfoFragmentPagerAdapter.USER_INFO_4) {
                if (isConditionsFulfilled()) {
                    if (currentPage == UserInfoFragmentPagerAdapter.USER_INFO_3) {
                        nextButton.text = getText(R.string.user_info_done)
                        pagerAdapter.notifyDataSetChanged()
                    }
                    if (currentPage == UserInfoFragmentPagerAdapter.USER_INFO_1) {
                        previousButton.visibility = View.VISIBLE
                    }
                    currentPage += 1
                    viewPager.setCurrentItem(currentPage, true)
                    changeCircleColor(MOVE_NEXT_PAGE)
                } else {
                    Toast.makeText(applicationContext, "선택되지 않은 항목이 존재합니다", Toast.LENGTH_SHORT).show()
                }
            } else {
                SharedPreferenceUtil.set(applicationContext, "first_setting_user_info", false)
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }

    private fun isConditionsFulfilled(): Boolean {
        when (currentPage) {
            UserInfoFragmentPagerAdapter.USER_INFO_1 -> {
                var selectedMajorCount = 0
                MajorList.collegeAndMajors
                        .flatMap { it }
                        .filter { SharedPreferenceUtil.get(applicationContext, it, false) }
                        .forEach { selectedMajorCount += 1 }

                if (selectedMajorCount < 1) return false
            }
            UserInfoFragmentPagerAdapter.USER_INFO_3 -> {
                if (countInterestCategories(applicationContext) < 3) return false
            }
        }
        return true
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

    override fun onBackPressed() {
        if (isUserDoubleClickBackPressed) {
            setResult(Activity.RESULT_CANCELED)
            finish()
        } else {
            this.isUserDoubleClickBackPressed = true
            Toast.makeText(this, getString(R.string.press_back_again_for_exit), Toast.LENGTH_SHORT).show()
        }

        Handler().postDelayed({
            isUserDoubleClickBackPressed = false
        }, 2000)
    }

    companion object {
        const val MAX_PAGE_SIZE = 4
        const val MOVE_PREVIOUS_PAGE = true
        const val MOVE_NEXT_PAGE = false

        fun countInterestCategories(context: Context): Int {
            return SettingCategory.categories.count { getCategoryStatus(it, context) == SettingCategory.INTEREST_CATEGORY }
        }

        private fun getCategoryStatus(key: String, context: Context): Int {
            return SharedPreferenceUtil.get(context, key, SettingCategory.NORMAL_CATEGORY)
        }
    }
}
