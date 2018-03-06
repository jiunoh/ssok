package com.example.jiun.sookpam;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by jiun on 2018-02-23.
 */

public class WebFragAdapter extends FragmentStatePagerAdapter {
    private static int TAB_NUMBER = 3;

    public WebFragAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                WebRecommendFragment webRecommendFragment = WebRecommendFragment.newInstance();
                return webRecommendFragment;
            case 1:
                WebCommonFragment webCommonFragment = WebCommonFragment.newInstance();
                return webCommonFragment;
            case 2:
                WebDepartFragment webDepartFragment = WebDepartFragment.newInstance();
                return webDepartFragment;
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
                return "추천";
            case 1:
                return "공통";
            case 2:
                return "학부/학과";
            default:
                return null;
        }
    }

}
