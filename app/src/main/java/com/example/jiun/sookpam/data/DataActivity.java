package com.example.jiun.sookpam.data;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.jiun.sookpam.R;
import com.example.jiun.sookpam.RecyclerItemClickListener;
import com.example.jiun.sookpam.message.ContentActivity;
import com.example.jiun.sookpam.model.ContactDBManager;
import com.example.jiun.sookpam.model.RecordDBManager;
import com.example.jiun.sookpam.util.SharedPreferenceUtil;

import java.util.ArrayList;
import io.realm.Realm;

public class DataActivity extends AppCompatActivity {
    private RecordDBManager categoryManager;
    private String category;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        category = getIntent().getStringExtra("category");
        final RecyclerView recyclerView = findViewById(R.id.data_recycler_view);

        final ArrayList<DataItem> dataItems = new ArrayList<>();
        ArrayList<String> response = getDataByCategory(category);
        for (String data : response) {
            DataItem dataItem = new DataItem();

            if (data.contains("[Web발신]\n"))
                data = data.replace("[Web발신]\n", "");

            String title;
            if(data.length() >=20) {
                title = data.substring(0, 20);
            }
            else {
                title = data;
            }
            title = title.replace("\r\n", "");
            dataItem.setTitle(title);
            dataItem.setBody(data);
            dataItems.add(dataItem);
        }

        DataRecyclerAdapter adapter = new DataRecyclerAdapter(dataItems);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                DataItem data = dataItems.get(position);
                showMessageBody(data);
            }
        }));
    }

    private void showMessageBody(DataItem data) {
        Intent intent = new Intent(this, ContentActivity.class);
        intent.putExtra("division",category);
        intent.putExtra("title", data.getTitle());
        intent.putExtra("body", data.getBody());
        startActivity(intent);
    }

    private ArrayList<String> getDataByCategory(String category) {
        categoryManager = new RecordDBManager(Realm.getDefaultInstance());
        ArrayList<String> response;
        if (!category.equals("학교"))
            response = categoryManager.getDataByCategory(category);
        else
            response = handleCategoryUniv();


        return response;
    }

    private ArrayList<String> handleCategoryUniv() {
        ContactDBManager contactDBManager =  (ContactDBManager)getApplicationContext();
        ArrayList<String> categoryList = contactDBManager.getDepartmentList();
        ArrayList<String> response = new ArrayList<>();
        for (String category : categoryList) {
            if (!SharedPreferenceUtil.get(this, category, false))
                response.addAll(categoryManager.getDataByCategory(category));
        }
        return response;
    }
}