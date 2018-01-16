package com.example.jiun.sookpam;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

public class DataActivity extends AppCompatActivity {
    private CategoryDBManager categoryManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        categoryManager = new CategoryDBManager();
        getDataByCategory();
    }

    private void getDataByCategory() {
        ArrayList<String> response = categoryManager.getDateByCategory("소프트웨어학부");
        for (String data : response)
            Log.v("문자 내용", data);
    }
}
