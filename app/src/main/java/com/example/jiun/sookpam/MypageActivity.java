package com.example.jiun.sookpam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

public class MypageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        LinearLayout message_button = (LinearLayout) findViewById(R.id.message_layout);
        LinearLayout web_button = (LinearLayout) findViewById(R.id.web_layout);
        web_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToWebActivity();
            }
        });
        message_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMessageActivity();
            }
        });
    }

    private void goToWebActivity() {
        Intent intent = new Intent(this, ViewPagerWebActivity.class);
        startActivity(intent);
    }

    private void goToMessageActivity() {
        Intent intent = new Intent(this, ViewPagerMainActivity.class);
        startActivity(intent);
    }
}
