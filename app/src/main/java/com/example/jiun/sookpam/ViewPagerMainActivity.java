package com.example.jiun.sookpam;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.*;
import com.example.jiun.sookpam.clip.ClipContent;
import com.example.jiun.sookpam.searchable.SearchableActivity;
import com.example.jiun.sookpam.user.info.UserInfoActivity;
import com.example.jiun.sookpam.util.SharedPreferenceUtil;
import io.realm.Realm;

public class ViewPagerMainActivity extends AppCompatActivity implements MyClipFragment.OnListFragmentInteractionListener{
    ImageButton searchImageButton;
    final static int USER_ACTIVITY_REQUEST = 1;
    Toolbar vpToolbar;
    ViewPager viewPager;
    ImageView icon_message, icon_web, icon_mypage;
    TextView nav_message, nav_web, nav_mypage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_main);

        if (isFirstUserInfoSetting()) {
            Intent intent = new Intent(this, UserInfoActivity.class);
            startActivityForResult(intent, USER_ACTIVITY_REQUEST);
        } else {
            setFragments();
        }
    }

    private void setFragments() {
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

        icon_message = findViewById(R.id.icon_message);
        icon_web = findViewById(R.id.icon_web);
        icon_mypage = findViewById(R.id.icon_mypage);
        nav_message = findViewById(R.id.nav_message);
        nav_web = findViewById(R.id.nav_web);
        nav_mypage = findViewById(R.id.nav_mypage);
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
        icon_message.setImageResource(R.drawable.message_selected);
        icon_web.setImageResource(R.drawable.web_default);
        icon_mypage.setImageResource(R.drawable.mypage_default);
        nav_message.setTextColor(getResources().getColor(R.color.colorPrimary));
        nav_mypage.setTextColor(getResources().getColor(R.color.colorDarkGray));
        nav_web.setTextColor(getResources().getColor(R.color.colorDarkGray));
        MessageFragAdapter messageFragAdapter = new MessageFragAdapter(getSupportFragmentManager());
        viewPager.setAdapter(messageFragAdapter);
    }

    private void goToWebTab() {
        icon_message.setImageResource(R.drawable.message_default);
        icon_web.setImageResource(R.drawable.web_selected);
        icon_mypage.setImageResource(R.drawable.mypage_default);
        nav_message.setTextColor(getResources().getColor(R.color.colorDarkGray));
        nav_web.setTextColor(getResources().getColor(R.color.colorPrimary));
        nav_mypage.setTextColor(getResources().getColor(R.color.colorDarkGray));
        WebFragAdapter webFragAdapter = new WebFragAdapter(getSupportFragmentManager());
        viewPager.setAdapter(webFragAdapter);
    }

    private void goToMypageTab() {
        icon_message.setImageResource(R.drawable.message_default);
        icon_web.setImageResource(R.drawable.web_default);
        icon_mypage.setImageResource(R.drawable.mypage_selected);
        nav_message.setTextColor(getResources().getColor(R.color.colorDarkGray));
        nav_web.setTextColor(getResources().getColor(R.color.colorDarkGray));
        nav_mypage.setTextColor(getResources().getColor(R.color.colorPrimary));
        MyFragAdapter myFragAdapter = new MyFragAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myFragAdapter);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == USER_ACTIVITY_REQUEST) {
            if (resultCode == RESULT_OK) {
                setFragments();
            }
            else {
                finish();
            }
        }
    }

    @Override
    public void onListFragmentInteraction(ClipContent.ClipItem item) {

    }
}
