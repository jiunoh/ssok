package com.example.jiun.sookpam.user

import android.support.v4.app.*

internal class SimpleFragmentPagerAdapter(fm: FragmentManager, private val mNumbOfTabs: Int) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            USER_INFO_1 -> UserInfo1Fragment()
            USER_INFO_2 -> UserInfo2Fragment()
            USER_INFO_3 -> UserInfo3Fragment()
            USER_INFO_4 -> UserInfo4Fragment()
            else -> null
        }
    }

    override fun getCount(): Int {
        return mNumbOfTabs
    }

    companion object {
        private val USER_INFO_1 = 0
        private val USER_INFO_2 = 1
        private val USER_INFO_3 = 2
        private val USER_INFO_4 = 3
    }
}
