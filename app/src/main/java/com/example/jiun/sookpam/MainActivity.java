package com.example.jiun.sookpam;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.jiun.sookpam.user.info.UserInfoActivity;
import com.example.jiun.sookpam.util.SharedPreferenceUtil;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {
    final static int MAX_PAGE_SIZE = 3;
    final static int USER_ACTIVITY_REQUEST = 1;
    private int[] tabDefaultIcons = {R.drawable.web_default, R.drawable.message_default, R.drawable.mypage_default};
    private int[] tabSelectedIcons = {R.drawable.web_selected,
            R.drawable.message_selected, R.drawable.mypage_selected};
    boolean doublePressBackToExit = false;

    SimpleViewPager mainViewPager;
    MainViewPagerAdapter mainViewPagerAdapter;
    Fragment currentFragment = null;
    TabLayout mainTabLayout;
    int currentPage = MainViewPagerAdapter.WEB_BASE_FRAGMENT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.ToolbarTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(this);
        initialize();
        setUpElements();

        if (isFirstUserInfoSetting()) {
            Intent intent = new Intent(this, UserInfoActivity.class);
            startActivityForResult(intent, USER_ACTIVITY_REQUEST);
        }
    }

    private void initialize() {
        mainTabLayout = findViewById(R.id.main_tab_layout);
        mainViewPager = findViewById(R.id.main_view_pager);
        mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), this, MAX_PAGE_SIZE);
    }

    private void setUpElements() {
        mainViewPager.setAdapter(mainViewPagerAdapter);
        mainTabLayout.setupWithViewPager(mainViewPager);
        setDefaultTabIcons();
        mainViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mainTabLayout));
        mainTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mainViewPager.setCurrentItem(tab.getPosition(), false);
                tab.setIcon(tabSelectedIcons[tab.getPosition()]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setIcon(tabDefaultIcons[tab.getPosition()]);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mainViewPager.setPagingEnabled(false);
        currentFragment = mainViewPagerAdapter.getItem(currentPage);
    }

    private void setDefaultTabIcons() {
        mainTabLayout.getTabAt(0).setIcon(tabSelectedIcons[0]);
        mainTabLayout.getTabAt(1).setIcon(tabDefaultIcons[1]);
        mainTabLayout.getTabAt(2).setIcon(tabDefaultIcons[2]);
    }

    private boolean isFirstUserInfoSetting() {
        return SharedPreferenceUtil.get(this, "first_setting_user_info", true);
    }

    @Override
    public void onBackPressed() {
        if (doublePressBackToExit) {
            super.onBackPressed();
            return;
        } else {
            this.doublePressBackToExit = true;
            Toast.makeText(this, getText(R.string.press_back_again_for_exit), Toast.LENGTH_SHORT).show();
        }
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doublePressBackToExit = false;
            }
        }, 2000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == USER_ACTIVITY_REQUEST) {
            if (resultCode == RESULT_OK) {
                initialize();
                setUpElements();
            } else {
                finish();
            }
        } else if (requestCode == 0) {
            MyPageBaseFragment.myPageViewPagerAdapter.notifyDataSetChanged();
        }
    }
}
