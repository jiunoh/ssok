package com.example.jiun.sookpam

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import com.example.jiun.sookpam.web.recommend.WebRecommendRecyclerFragment

class WebBaseViewPagerAdapter(fm: FragmentManager, context: Context, private val numberOfPage: Int) : FragmentStatePagerAdapter(fm) {
    private val tabTitles = arrayListOf(context.getString(R.string.recommend), context.getString(R.string.common), context.getString(R.string.depart))

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
            RECOMMEND_FRAGMENT -> WebRecommendRecyclerFragment()
            COMMON_FRAGMENT -> WebCommonTopicFragment()
            DEPART_FRAGMENT -> WebDepartmentFragment()
            else -> null
        }
    }

    override fun getCount(): Int {
        return numberOfPage
    }

    companion object {
        const val RECOMMEND_FRAGMENT = 0
        const val COMMON_FRAGMENT = 1
        const val DEPART_FRAGMENT = 2
    }
}