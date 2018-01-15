package com.example.jiun.sookpam;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.widget.ListView;
import com.example.jiun.sookpam.model.data.ContactVO;
import io.realm.RealmResults;

public class CategoryActivity extends AppCompatActivity {
    private final String CATEGORY = "category";
    private  CategoryManager categoryManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        ListView listView = (ListView) findViewById(R.id.category_listView);
        CategoryListviewAdapter adapter = new CategoryListviewAdapter();
        listView.setAdapter(adapter);

        categoryManager = new CategoryManager();
        RealmResults<ContactVO> results = categoryManager.getCategoryList();
        for(ContactVO record : results )
            adapter.addItem(record.class2);
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