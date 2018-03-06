package com.example.jiun.sookpam.searchable;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import com.example.jiun.sookpam.model.RecordDBManager;
import com.example.jiun.sookpam.model.vo.RecordVO;
import com.example.jiun.sookpam.util.ViewHolderFactory;
import java.util.ArrayList;
import java.util.Locale;

import io.realm.Realm;

public class SearchableRecyclerAdapter extends RecyclerView.Adapter {
    private ArrayList<SearchItem> searchableItems = new ArrayList<SearchItem>();
    private ArrayList<? extends SearchItem> responseList;

    SearchableRecyclerAdapter(ArrayList<SearchItem> items) {
        searchableItems = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       return ViewHolderFactory.create(parent, viewType);
    }

    @Override
    public int getItemViewType(int position) {
        return searchableItems.get(position).getItemViewType();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        searchableItems.get(position).onBindViewHolder(holder);
    }

    @Override
    public int getItemCount() {
        return searchableItems.size();
    }

    public void filter(String charText) {
        Log.v("filter: ", charText);
        charText = charText.toLowerCase(Locale.getDefault());
        responseList = getQuery(charText);
        searchableItems.clear();
        searchableItems.addAll(responseList);
        notifyDataSetChanged();
    }

    public void clear() {
        searchableItems.removeAll(responseList);
        notifyDataSetChanged();
    }


    private ArrayList<RecordVO> getQuery(String query) {
        RecordDBManager recordManager = new RecordDBManager(Realm.getDefaultInstance());
        return recordManager.contains(query);
    }
}