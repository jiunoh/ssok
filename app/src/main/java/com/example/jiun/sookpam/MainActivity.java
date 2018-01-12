package com.example.jiun.sookpam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jiun.sookpam.model.mms.MmsReader;
import com.example.jiun.sookpam.model.sms.SmsReader;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {
    SmsReader smsReader;
    MmsReader mmsReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);
        smsReader = new SmsReader();
        mmsReader = new MmsReader();
    }

    @Override
    protected void onResume() {
        super.onResume();
        smsReader.setSms(this);
        mmsReader.setMms(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
