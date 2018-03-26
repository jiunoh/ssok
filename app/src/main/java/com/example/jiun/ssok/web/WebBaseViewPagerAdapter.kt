package com.example.jiun.ssok.web

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import com.example.jiun.ssok.R
import com.example.jiun.ssok.web.common.WebCommonTopicFragment
import com.example.jiun.ssok.web.department.WebDepartmentFragment
import com.example.jiun.ssok.web.recommend.WebRecommendFragment

class WebBaseViewPagerAdapter(fm: FragmentManager, context: Context, private val numberOfPage: Int) : FragmentStatePagerAdapter(fm) {
    private val tabTitles = arrayListOf(context.getString(R.string.common), context.getString(R.string.depart), context.getString(R.string.recommend))

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
            RECOMMEND_FRAGMENT -> WebRecommendFragment()
            COMMON_FRAGMENT -> WebCommonTopicFragment()
            DEPART_FRAGMENT -> WebDepartmentFragment()
            else -> null
        }
    }

    override fun getCount(): Int {
        return numberOfPage
    }

    companion object {
        const val COMMON_FRAGMENT = 0
        const val DEPART_FRAGMENT = 1
        const val RECOMMEND_FRAGMENT = 2
    }
}