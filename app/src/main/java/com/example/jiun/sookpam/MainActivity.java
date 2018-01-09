package com.example.jiun.sookpam;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainListviewAdapter adapter = new MainListviewAdapter();
        ListView listView = (ListView) findViewById(R.id.main_listView);
        listView.setAdapter(adapter);

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.arrow), "demo category");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.arrow), "demo category");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.arrow), "demo category");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.arrow), "demo category");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(MainActivity.this ,"test toast", Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean loadItems(ArrayList<MainListItem> list) {
        MainListItem item;

        if (list == null) {
            list = new ArrayList<MainListItem>();
        }

        for (int i=0; i<4; i++) {
            item = new MainListItem();
            item.setButton(ContextCompat.getDrawable(this, R.drawable.arrow));
            item.setCategory("demo category");
            list.add(item);
        }

        return true;
    }
}
