package com.example.jiun.sookpam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MainListviewAdapter extends BaseAdapter {
    private ArrayList<MainListItem> mainItemList = new ArrayList<MainListItem>() ;

    @Override
    public int getCount() {
        return mainItemList.size() ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final Context context = viewGroup.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.main_listview_item, viewGroup, false);
        }

        TextView category = (TextView) convertView.findViewById(R.id.main_list_item);
        MainListItem mainListItem = mainItemList.get(position);
        category.setText(mainListItem.getCategory());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public Object getItem(int position) {
        return mainItemList.get(position) ;
    }

    public void addItem(String category) {
        MainListItem item = new MainListItem();
        item.setCategory(category);
        mainItemList.add(item);
    }


}
