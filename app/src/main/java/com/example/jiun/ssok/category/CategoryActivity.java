package com.example.jiun.ssok.category;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.widget.ListView;

import com.example.jiun.ssok.model.ContactDBManager;
import com.example.jiun.ssok.R;
import com.example.jiun.ssok.util.SharedPreferenceUtil;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    private final String CATEGORY = "category";
    private ContactDBManager contactDBManager;
    private CategoryListviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        ListView listView = (ListView) findViewById(R.id.category_listView);
        adapter = new CategoryListviewAdapter();
        listView.setAdapter(adapter);

        contactDBManager = (ContactDBManager) getApplicationContext();
        ArrayList<String> categoryList = contactDBManager.getCategoryList();
        for (String category : categoryList)
            adapter.addItem(category);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ListView listView = (ListView) findViewById(R.id.category_listView);
        SparseBooleanArray checked = listView.getCheckedItemPositions();
        CategoryListviewAdapter adapter = (CategoryListviewAdapter) listView.getAdapter();
        final int size = adapter.getCount();
        String category;

        for (int i = 0; i < size; i++) {
            CategoryItem item = (CategoryItem) adapter.getItem(i);
            category = item.getCategory();
            if (checked.get(i)) {
                SharedPreferenceUtil.set(this, category, true);
            } else {
                SharedPreferenceUtil.set(this, category, false);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ListView listView = (ListView) findViewById(R.id.category_listView);
        CategoryListviewAdapter adapter = (CategoryListviewAdapter) listView.getAdapter();
        final int size = adapter.getCount();
        String category;

        for (int i = 0; i < size; i++) {
            CategoryItem item = (CategoryItem) adapter.getItem(i);
            category = item.getCategory();
            final boolean value = SharedPreferenceUtil.get(this, category, false);
            listView.setItemChecked(i, value);
        }
    }
}