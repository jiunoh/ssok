package com.example.jiun.sookpam;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import io.realm.Realm;

public class DataActivity extends AppCompatActivity {
    private CategoryDBManager categoryManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        String category = getIntent().getStringExtra("category");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.data_recycler_view);
        ArrayList<DataItem> dataItems = new ArrayList<DataItem>();
        ArrayList<String> response =  getDataByCategory(category);

        for (String data : response){
            DataItem dataItem = new DataItem();
            dataItem.setTitle(category);
            dataItem.setBody(data);
            dataItems.add(dataItem);
        }
        DataRecyclerAdapter adapter = new DataRecyclerAdapter(dataItems);
        recyclerView.setAdapter(adapter);
    }


    private ArrayList<String> getDataByCategory(String category) {
        categoryManager = new CategoryDBManager(Realm.getDefaultInstance());
        ArrayList<String> response = new ArrayList<String>();

        if(!category.equals("학교"))
            response = categoryManager.getDataByCategory(category);
        else
            response = handleCategoryUniv();
       return response;
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
