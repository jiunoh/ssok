package com.example.jiun.sookpam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CategoryListviewAdapter extends BaseAdapter {
    private ArrayList<CategoryListItem> categoryItemList = new ArrayList<CategoryListItem>() ;

    @Override
    public int getCount() {
        return categoryItemList.size() ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final Context context = viewGroup.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.category_listview_item, viewGroup, false);
        }

        TextView category = (TextView) convertView.findViewById(R.id.category);
        CategoryListItem categoryListItem = categoryItemList.get(position);
        category.setText(categoryListItem.getCategory());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return categoryItemList.get(position) ;
    }

    public void addItem(String category) {
        CategoryListItem item = new CategoryListItem();
        item.setCategory(category);
        categoryItemList.add(item);
    }

}
