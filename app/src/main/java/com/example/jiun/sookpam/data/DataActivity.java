package com.example.jiun.sookpam.data;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
    private Toolbar toolbar;
    private String category;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        category = getIntent().getStringExtra("category");
        toolbar = (Toolbar)findViewById(R.id.data_toolbar);
        toolbar.setTitle(category);
        setSupportActionBar(toolbar);
        setToolbar();
        final RecyclerView recyclerView = findViewById(R.id.data_recycler_view);

        final ArrayList<DataItem> dataItems = new ArrayList<>();
        ArrayList<String> response = getDataByDivision(category);
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

    private void setToolbar() {
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void showMessageBody(DataItem data) {
        Intent intent = new Intent(this, ContentActivity.class);
        intent.putExtra("division",category);
        intent.putExtra("title", data.getTitle());
        intent.putExtra("body", data.getBody());
        startActivity(intent);
    }

    private ArrayList<String> getDataByDivision(String division) {
        categoryManager = new RecordDBManager(Realm.getDefaultInstance());
        ArrayList<String> response;
        if (!division.equals("공지"))
            response = categoryManager.getDataByDivision(division);
        else
            response = handleUnclipedCategories();


        return response;
    }

    private ArrayList<String> handleUnclipedCategories() {
        ContactDBManager contactDBManager =  (ContactDBManager)getApplicationContext();
        ArrayList<String> divisionList = contactDBManager.getDepartmentList();
        ArrayList<String> response = new ArrayList<>();
        for (String division : divisionList) {
            if (!SharedPreferenceUtil.get(this, division, false))
                response.addAll(categoryManager.getDataByDivision(division));
        }
        return response;
    }
}