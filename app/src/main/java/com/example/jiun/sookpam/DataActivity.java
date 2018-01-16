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
        String category = getIntent().getStringExtra("category");
        Log.v("DataActivity",category);
        categoryManager = new CategoryDBManager();
        getDataByCategory(category);
    }

    private void getDataByCategory(String category) {
        ArrayList<String> response = categoryManager.getDateByCategory(category);
        for (String data : response)
            Log.v("문자 내용", data);
    }
}
