package com.example.jiun.sookpam;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<CategoryListItem> items = new ArrayList<CategoryListItem>();

        MainListviewAdapter adapter = new MainListviewAdapter(this, R.layout.main_listview_item, items, this);
        ListView listView = (ListView) findViewById(R.id.main_listView);
        listView.setAdapter(adapter);

    }
}
