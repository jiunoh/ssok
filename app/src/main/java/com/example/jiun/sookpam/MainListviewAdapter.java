package com.example.jiun.sookpam;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainListviewAdapter extends BaseAdapter {
    private ArrayList<MainItem> mainItemList = new ArrayList<MainItem>();

    @Override
    public int getCount() {
        return mainItemList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final Context context = viewGroup.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.main_listview_item, viewGroup, false);
        }

        ImageView button = (ImageView) convertView.findViewById(R.id.main_button);
        TextView category = (TextView) convertView.findViewById(R.id.main_list_item);

        MainItem mainListItem = mainItemList.get(position);

        button.setImageDrawable(mainListItem.getButton());
        category.setText(mainListItem.getCategory());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return mainItemList.get(position);
    }

    public void addItem(Drawable image, String category) {
        MainItem item = new MainItem();
        item.setCategory(category);
        item.setButton(image);
        mainItemList.add(item);
    }

    public void clear() {
        mainItemList.clear();
    }
}
