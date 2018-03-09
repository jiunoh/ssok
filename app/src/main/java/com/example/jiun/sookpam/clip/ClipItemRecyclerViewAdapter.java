package com.example.jiun.sookpam.clip;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import com.example.jiun.sookpam.model.DualModel;
import com.example.jiun.sookpam.util.ViewHolderFactory;
import java.util.ArrayList;
import io.realm.Realm;

public class ClipItemRecyclerViewAdapter extends RecyclerView.Adapter {
    private ArrayList<DualModel> itemList;
    private ArrayList<? extends DualModel> responseList;

    public ClipItemRecyclerViewAdapter(ArrayList<DualModel> items) {
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

    public void filter() {
        Log.v("filter", "filter");
        ClipDBManager dbManager = new ClipDBManager(Realm.getDefaultInstance());
        itemList.clear();
        responseList = dbManager.select();
        itemList.addAll(responseList);
        notifyDataSetChanged();
    }
 }
