package com.example.jiun.sookpam.searchable;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.jiun.sookpam.model.DualModel;
import com.example.jiun.sookpam.model.RecordDBManager;
import com.example.jiun.sookpam.server.WebFilter;
import com.example.jiun.sookpam.util.ViewHolderFactory;
import java.util.ArrayList;
import java.util.Locale;
import io.realm.Realm;

public class SearchableRecyclerAdapter extends RecyclerView.Adapter {
    private ArrayList<DualModel> itemList;
    private ArrayList<? extends DualModel> responseList;

    public SearchableRecyclerAdapter(ArrayList<DualModel> items) {
        itemList = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ViewHolderFactory.create(parent, viewType);
    }

    @Override
    public int getItemViewType(int position) {
        return itemList.get(position).getItemViewType();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        itemList.get(position).onBindViewHolder(holder);
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public boolean filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        itemList.addAll(WebFilter.webFilter(charText));
        notifyDataSetChanged();
        realmFilter(charText);
        notifyDataSetChanged();
        return itemList.size() == 0;
    }

    private void realmFilter(String charText) {
        RecordDBManager recordManager = new RecordDBManager(Realm.getDefaultInstance());
        responseList = recordManager.contains(charText);
        itemList.clear();
        itemList.addAll(responseList);
        notifyDataSetChanged();
    }

    public void clear() {
        itemList.removeAll(responseList);
        itemList.clear();
        notifyDataSetChanged();
    }
}