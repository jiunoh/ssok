package com.example.jiun.sookpam

import android.content.Context
import android.support.v4.app.*
import android.support.v4.view.PagerAdapter

class MainViewPagerAdapter(fm: FragmentManager, context: Context, private val numberOfPage: Int) : FragmentStatePagerAdapter(fm) {
    private val tabTitles = arrayListOf(context.getString(R.string.web), context.getString(R.string.message), context.getString(R.string.my_page))

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles[position]
    }


    override fun getItemPosition(item: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    override fun getItem(position: Int): Fragment? {
        if (position < 0 || position >= numberOfPage)
            return null
        return when (position) {
            WEB_BASE_FRAGMENT -> WebBaseFragment()
            MESSAGE_BASE_FRAGMENT -> MessageBaseFragment()
            MY_PAGE_BASE_FRAGMENT -> MyPageBaseFragment()
            else -> null
        }
    }

    override fun getCount(): Int {
        return numberOfPage
    }

    companion object {
        const val WEB_BASE_FRAGMENT = 0
        const val MESSAGE_BASE_FRAGMENT = 1
        const val MY_PAGE_BASE_FRAGMENT = 2
    }
}