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
        getDataByCategory(category);
    }

    private void getDataByCategory(String category) {
        categoryManager = new CategoryDBManager();
        ArrayList<String> response = new ArrayList<String>();
        if(category.equals("학교"))
            response = handleCategoryUniv();
        else
            response = categoryManager.getDataByCategory(category);

        for (String data : response)
            Log.v("문자 내용", data);
    }

    private ArrayList<String> handleCategoryUniv() {
        ContactDBManager contactDBManager =  (ContactDBManager)getApplicationContext();
        ArrayList<String> categoryList = contactDBManager.getCategoryList();
        ArrayList<String> response = new ArrayList<String>();
        for (String category :categoryList) {
            if (!SharedPreferenceUtil.get(this, category, false))
                response.addAll(categoryManager.getDataByCategory(category));
        }
        return response;
    }
}
