package com.example.jiun.sookpam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        LinearLayout settingCategory = (LinearLayout) findViewById(R.id.setting_category);
        settingCategory.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                go();
            }
        });
    }

    private void go() {
        Intent intent = new Intent(this, CategoryActivity.class);
        startActivity(intent);
    }
}