package com.example.jiun.sookpam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

public class ViewPagerWebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_web);

        LinearLayout message_button = (LinearLayout) findViewById(R.id.message_layout);
        LinearLayout mypage_button = (LinearLayout) findViewById(R.id.mypage_layout);
        message_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMessageActivity();
            }
        });
        mypage_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMypageActivity();
            }
        });

    }

    private void goToMessageActivity() {
        Intent intent = new Intent(this, ViewPagerMainActivity.class);
        startActivity(intent);
    }

    private void goToMypageActivity() {
        Intent intent = new Intent(this, MypageActivity.class);
        startActivity(intent);
    }
}
