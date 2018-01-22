package com.example.jiun.sookpam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import java.util.ArrayList;
import io.realm.Realm;

public class DataActivity extends AppCompatActivity {
    private CategoryDBManager categoryManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        String category = getIntent().getStringExtra("category");
        Log.v("DataActivity", category);
        final RecyclerView recyclerView = findViewById(R.id.data_recycler_view);

        final ArrayList<DataItem> dataItems = new ArrayList<>();
        ArrayList<String> response = getDataByCategory(category);
        for (String data : response) {
            DataItem dataItem = new DataItem();

            if (data.contains("[Web발신]\n"))
                data = data.replace("[Web발신]\n", "");

            String title = data.substring(0, 20);
            title = title.replace("\r\n", "");
            dataItem.setTitle(title);
            String body = data.substring(20, data.length()-1);
            dataItem.setBody(data);

            dataItems.add(dataItem);
        }

        DataRecyclerAdapter adapter = new DataRecyclerAdapter(dataItems);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                DataItem data = dataItems.get(position);
                showMessageBody(data);
            }
        }));
    }

    private void showMessageBody(DataItem data) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("title", data.getTitle());
        intent.putExtra("body", data.getBody());
        startActivity(intent);
    }

    private ArrayList<String> getDataByCategory(String category) {
        categoryManager = new CategoryDBManager(Realm.getDefaultInstance());
        ArrayList<String> response = new ArrayList<String>();
        if (!category.equals("학교"))
            response = categoryManager.getDataByCategory(category);
        else
            response = handleCategoryUniv();


        return response;
    }

    private ArrayList<String> handleCategoryUniv() {
        ContactDBManager contactDBManager = (ContactDBManager) getApplicationContext();
        ArrayList<String> categoryList = contactDBManager.getCategoryList();
        ArrayList<String> response = new ArrayList<String>();
        for (String category : categoryList) {
            if (!SharedPreferenceUtil.get(this, category, false))
                response.addAll(categoryManager.getDataByCategory(category));
        }
        return response;
    }
}