package com.example.jiun.sookpam;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jiun.sookpam.searchable.SearchableActivity;
import com.example.jiun.sookpam.message.MessageContract;
import com.example.jiun.sookpam.user.UserInfoActivity;
import com.example.jiun.sookpam.util.SharedPreferenceUtil;
import com.gun0912.tedpermission.PermissionListener;
import com.example.jiun.sookpam.message.MessagePresenter;
import com.gun0912.tedpermission.TedPermission;

import io.realm.Realm;

import org.jetbrains.annotations.NotNull;

public class ViewPagerMainActivity extends AppCompatActivity implements MessageContract.View {
    MessageContract.Presenter presenter;
    Toolbar vpToolbar;
    ViewPager viewPager;
    ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_main);

        if (isFirstUserInfoSetting()) {
            Intent intent = new Intent(this, UserInfoActivity.class);
            startActivity(intent);
        }
        setTitle("");

        initialize();

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
        Realm.init(this);

        progressbar = findViewById(R.id.main_refresh_progressbar);
        setPresenter(new MessagePresenter(getApplicationContext(), ViewPagerMainActivity
                .this, progressbar));

        vpToolbar = (Toolbar) findViewById(R.id.view_pager_toolbar);
        setSupportActionBar(vpToolbar);
    }


    private boolean isFirstUserInfoSetting() {
        return SharedPreferenceUtil.get(this, "first_setting_user_info", true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu toolbar) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.fragments_toolbar, toolbar);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_button:
                Intent intent = new Intent(this, SearchableActivity.class);
                startActivity(intent);
                return true;
            case R.id.refresh_button:
                ActionMenuItemView refreshButton = findViewById(R.id.refresh_button);
                Animation rotateAnimation = UIAnimation.Companion.setRotateAnimation(refreshButton);
                refreshButton.startAnimation(rotateAnimation);
                presenter.start();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

    @Override
    protected void onPause() {
        super.onPause();
        presenter.cancelMessageAsyncTask();
    }

    @Override
    public void showPermissionMessage(@NotNull PermissionListener permissionListener) {
        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleTitle(getString(R.string.read_sms_request_title))
                .setRationaleMessage(getString(R.string.read_sms_request_detail))
                .setDeniedTitle(getString(R.string.denied_read_sms_title))
                .setDeniedMessage(getString(R.string.denied_read_sms_detail))
                .setGotoSettingButtonText(getString(R.string.move_setting))
                .setPermissions(android.Manifest.permission.READ_SMS)
                .check();
    }

    @Override
    public void showToastMessage(@NotNull String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public MessageContract.Presenter getPresenter() {
        return presenter;
    }

    @Override
    public void setPresenter(MessageContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
