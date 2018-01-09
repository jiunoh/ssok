package com.example.jiun.sookpam;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jiun.sookpam.model.mms.MmsReader;
import com.example.jiun.sookpam.model.sms.SmsReader;
import com.example.jiun.sookpam.model.sms.SmsReceiver;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {
    BroadcastReceiver receiver;
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
        //smsReader.getSmsDetails(this);
        registerSmsReceiver();
        mmsReader.getMmsDetails(this);
    }

    private void registerSmsReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        receiver = new SmsReceiver();
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
}
