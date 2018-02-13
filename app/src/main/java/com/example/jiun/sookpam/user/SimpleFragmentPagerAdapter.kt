package com.example.jiun.sookpam.user

import android.support.v4.app.*

internal class SimpleFragmentPagerAdapter(fm: FragmentManager, private val numberOfPages: Int) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        if (position < 0 || position >= numberOfPages)
            return null
        return when (position) {
                USER_INFO_1 -> UserInfo1Fragment()
                USER_INFO_2 -> UserInfo2Fragment()
                USER_INFO_3 -> UserInfo3Fragment()
                USER_INFO_4 -> UserInfo4Fragment()
                else -> null
        }
    }

    override fun getCount(): Int {
        return numberOfPages
    }

    companion object {
        private val USER_INFO_1 = 0
        private val USER_INFO_2 = 1
        private val USER_INFO_3 = 2
        private val USER_INFO_4 = 3
    }
}
