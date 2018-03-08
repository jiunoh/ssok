package com.example.jiun.sookpam.clip;

import android.util.Log;

import com.example.jiun.sookpam.searchable.DualModel;
import com.example.jiun.sookpam.searchable.SearchableRecyclerAdapter;
import java.util.ArrayList;
import io.realm.Realm;

public class ClipItemRecyclerViewAdapter extends SearchableRecyclerAdapter {
    private ArrayList<DualModel> itemList;
    private ArrayList<? extends DualModel> responseList;

    public ClipItemRecyclerViewAdapter(ArrayList<DualModel> items) {
        super(items);
        itemList = items;
    }

    public void loadData() {
        ClipDBManager dbManager = new ClipDBManager(Realm.getDefaultInstance());
        responseList = dbManager.select();
        itemList.clear();
        itemList.addAll(responseList);
        notifyDataSetChanged();
    }
 }
