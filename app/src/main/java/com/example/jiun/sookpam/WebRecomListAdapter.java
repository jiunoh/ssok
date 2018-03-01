package com.example.jiun.sookpam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class WebRecomListAdapter extends BaseAdapter {
    private ArrayList<WebRecomItem> webRecomItems = new ArrayList<WebRecomItem>();

    public static class WebRecomViewHolder {
        TextView category;
        TextView title;
    }

    @Override
    public int getCount() {
        return webRecomItems.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();
        WebRecomListAdapter.WebRecomViewHolder holder;
        holder = new WebRecomViewHolder();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.message_depart_item, parent, false);
            holder.category = (TextView) convertView.findViewById(R.id.message_depart_category);
            holder.title = (TextView) convertView.findViewById(R.id.message_depart_title);
            convertView.setTag(holder);
        } else
            holder = (WebRecomListAdapter.WebRecomViewHolder) convertView.getTag();

        WebRecomItem webRecomItem = webRecomItems.get(position);

        holder.title.setText(webRecomItem.getTitle());
        holder.category.setText(webRecomItem.getCategory());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return webRecomItems.get(position);
    }

    public void addItem(String title, String category) {
        WebRecomItem item = new WebRecomItem();
        item.setCategory(category);
        item.setTitle(title);
        webRecomItems.add(item);
    }

    public void clear() {
        webRecomItems.clear();
    }
}