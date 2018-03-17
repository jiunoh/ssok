package com.example.jiun.sookpam

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import com.example.jiun.sookpam.user.info.CategoryInterestFragment

class MyPageBaseViewPagerAdapter(fm: FragmentManager, context: Context, private val numberOfPage: Int) : FragmentStatePagerAdapter(fm) {
    private val tabTitles = arrayListOf(context.getString(R.string.my_topic), context.getString(R.string.my_info), context.getString(R.string.my_clip))

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
            MY_TOPIC_FRAGMENT -> CategoryInterestFragment()
            MY_INFO_FRAGMENT -> MyInfoFragment()
            MY_CLIP_FRAGMENT -> MyClipFragment()
            else -> null
        }
    }

    override fun getCount(): Int {
        return numberOfPage
    }

    companion object {
        const val MY_TOPIC_FRAGMENT = 0
        const val MY_INFO_FRAGMENT = 1
        const val MY_CLIP_FRAGMENT = 2
    }
}