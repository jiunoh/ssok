package com.example.jiun.sookpam;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by jiun on 2018-02-20.
 */

public class MessageFragAdapter extends FragmentStatePagerAdapter {
    private static int TAB_NUMBER = 3;

    public MessageFragAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                MessageRecomFragment messageRecomFragment = MessageRecomFragment.newInstance();
                return messageRecomFragment;
            case 1:
                MessageCommonFragment messageCommonFragment = MessageCommonFragment.newInstance();
                return messageCommonFragment;
            case 2:
                MessageDepartFragment messageDepartFragment = MessageDepartFragment.newInstance();
                return messageDepartFragment;
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
