package com.example.jiun.sookpam;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.view.animation.Animation;
import android.widget.*;

import com.example.jiun.sookpam.searchable.SearchableActivity;
import com.example.jiun.sookpam.message.MessageContract;
import com.example.jiun.sookpam.user.info.UserInfoActivity;
import com.example.jiun.sookpam.util.SharedPreferenceUtil;
import com.example.jiun.sookpam.web.recommend.WebRecommendFragment;
import com.gun0912.tedpermission.PermissionListener;
import com.example.jiun.sookpam.message.MessagePresenter;
import com.gun0912.tedpermission.TedPermission;

import io.realm.Realm;

import org.jetbrains.annotations.NotNull;

public class ViewPagerMainActivity extends AppCompatActivity{
    Toolbar vpToolbar;
    ViewPager viewPager;
    ImageButton searchImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_main);

        if (isFirstUserInfoSetting()) {
            Intent intent = new Intent(this, UserInfoActivity.class);
            startActivity(intent);
        }
        setTitle("");

        Realm.init(this);

        initialize();

        vpToolbar = (Toolbar) findViewById(R.id.view_pager_toolbar);
        setSupportActionBar(vpToolbar);

        viewPager = (ViewPager) findViewById(R.id.view_pager_main);
        TabLayout upperTabs = (TabLayout) findViewById(R.id.upper_tab_layout);
        upperTabs.setupWithViewPager(viewPager);

        MessageFragAdapter messageFragAdapter = new MessageFragAdapter(getSupportFragmentManager());
        viewPager.setAdapter(messageFragAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(upperTabs));
        upperTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        LinearLayout message_button = (LinearLayout) findViewById(R.id.message_layout);
        LinearLayout web_button = (LinearLayout) findViewById(R.id.web_layout);
        LinearLayout mypage_button = (LinearLayout) findViewById(R.id.mypage_layout);
        message_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMessageTab();
            }
        });
        web_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToWebTab();
            }
        });
        mypage_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMypageTab();
            }
        });
    }

    private void initialize() {
        searchImageButton = findViewById(R.id.main_search_image_btn);
        searchImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchableActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean isFirstUserInfoSetting() {
        return SharedPreferenceUtil.get(this, "first_setting_user_info", true);
    }

    private void goToMessageTab() {
        MessageFragAdapter messageFragAdapter = new MessageFragAdapter(getSupportFragmentManager());
        viewPager.setAdapter(messageFragAdapter);
    }

    private void goToWebTab() {
        WebFragAdapter webFragAdapter = new WebFragAdapter(getSupportFragmentManager());
        viewPager.setAdapter(webFragAdapter);
    }

    private void goToMypageTab() {
        MyFragAdapter myFragAdapter = new MyFragAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myFragAdapter);
    }
}
