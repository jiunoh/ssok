package com.example.jiun.sookpam;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.widget.ListView;

public class CategoryActivity extends AppCompatActivity {
    private final String CATEGORY = "category";

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

    @Override
    protected void onPause() {
        super.onPause();
        ListView listView = (ListView) findViewById(R.id.category_listView);
        SparseBooleanArray checked = listView.getCheckedItemPositions();
        final int size = listView.getAdapter().getCount();

        for (int i = 0; i < size; i++) {
            if (checked.get(i)) {
                SharedPreferenceUtil.set(this, CATEGORY + i, true);
            } else {
                SharedPreferenceUtil.set(this, CATEGORY + i, false);
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        ListView listView = (ListView) findViewById(R.id.category_listView);
        final int size = listView.getAdapter().getCount();
        for (int i = 0; i < size; i++) {
            final boolean value = SharedPreferenceUtil.get(this, CATEGORY + i, false);
            listView.setItemChecked(i, value);
        }
    }
}