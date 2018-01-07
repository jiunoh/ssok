package com.example.jiun.sookpam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {
    private RealmConfiguration realmConfig;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);
        realmConfig = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(realmConfig);
    }
}
