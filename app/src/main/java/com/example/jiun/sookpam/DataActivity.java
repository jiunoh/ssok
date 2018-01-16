package com.example.jiun.sookpam;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class DataActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.data_recycler_view);
        ArrayList<DataItem> dataItems = new ArrayList<DataItem>();
        for (int i=0; i<4; i++) {
            DataItem dataItem = new DataItem();
            dataItem.setTitle("demo title");
            dataItem.setBody("demo body");
            dataItems.add(dataItem);
        }
        DataRecyclerAdapter adapter = new DataRecyclerAdapter(dataItems);
        recyclerView.setAdapter(adapter);
    }

}
