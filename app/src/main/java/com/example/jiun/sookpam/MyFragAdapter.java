package com.example.jiun.sookpam;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by jiun on 2018-02-23.
 */

public class MyFragAdapter extends FragmentStatePagerAdapter {
    private static int TAB_NUMBER = 3;

    public MyFragAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                MyTopicFragment myTopicFragment = MyTopicFragment.newInstance();
                return myTopicFragment;
            case 1:
                MyInfoFragment myInfoFragment = MyInfoFragment.newInstance();
                return myInfoFragment;
            case 2:
                MyScrapFragment myScrapFragment = MyScrapFragment.newInstance();
                return myScrapFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return TAB_NUMBER;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "관심사";
            case 1:
                return "개인정보";
            case 2:
                return "보관함";
            default:
                return null;
        }
    }

}
