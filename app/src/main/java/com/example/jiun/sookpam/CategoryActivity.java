package com.example.jiun.sookpam;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        ListView listView = (ListView) findViewById(R.id.category_listView);
        CategoryListviewAdapter adapter = new CategoryListviewAdapter();
        listView.setAdapter(adapter);

        adapter.addItem("demo category 1");
        adapter.addItem("demo category 2");
        adapter.addItem("demo category 3");
        adapter.addItem("demo category 4");
    }
}