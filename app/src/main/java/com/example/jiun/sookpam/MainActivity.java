package com.example.jiun.sookpam;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.example.jiun.sookpam.user.info.UserInfoActivity;
import com.example.jiun.sookpam.util.SharedPreferenceUtil;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {
    final static int MAX_PAGE_SIZE = 3;
    final static int USER_ACTIVITY_REQUEST = 1;

    SimpleViewPager mainViewPager;
    MainViewPagerAdapter mainViewPagerAdapter;
    Fragment currentFragment = null;
    TabLayout mainTabLayout;
    int currentPage = MainViewPagerAdapter.WEB_BASE_FRAGMENT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);

        if (isFirstUserInfoSetting()) {
            Intent intent = new Intent(this, UserInfoActivity.class);
            startActivityForResult(intent, USER_ACTIVITY_REQUEST);
        } else {
            initialize();
        }
    }

    private void initialize() {
        mainTabLayout = findViewById(R.id.main_tab_layout);
        mainViewPager = findViewById(R.id.main_view_pager);
        mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), this, MAX_PAGE_SIZE);
        mainViewPager.setAdapter(mainViewPagerAdapter);
        mainTabLayout.setupWithViewPager(mainViewPager);
        mainViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mainTabLayout));
        mainTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mainViewPager.setCurrentItem(tab.getPosition(), false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mainViewPager.setPagingEnabled(false);
        mainViewPager.setOffscreenPageLimit(MAX_PAGE_SIZE);
        currentFragment = mainViewPagerAdapter.getItem(currentPage);
    }

    private boolean isFirstUserInfoSetting() {
        return SharedPreferenceUtil.get(this, "first_setting_user_info", true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == USER_ACTIVITY_REQUEST) {
            if (resultCode == RESULT_OK) {
                initialize();
            } else {
                finish();
            }
        }
    }
}
