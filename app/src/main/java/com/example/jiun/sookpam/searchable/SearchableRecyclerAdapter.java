package com.example.jiun.sookpam.searchable;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import com.example.jiun.sookpam.model.RecordDBManager;
import com.example.jiun.sookpam.server.ApiUtils;
import com.example.jiun.sookpam.server.RecordResponse;
import com.example.jiun.sookpam.server.RecordService;
import com.example.jiun.sookpam.server.SearchableService;
import com.example.jiun.sookpam.util.ViewHolderFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchableRecyclerAdapter extends RecyclerView.Adapter {
    private ArrayList<SearchItem> searchableItems;
    private ArrayList<? extends SearchItem> responseList;
    private RecordService service;

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
        charText = charText.toLowerCase(Locale.getDefault());
        realmFilter(charText);
        webFilter(charText);
    }

    private void realmFilter(String charText) {
        RecordDBManager recordManager = new RecordDBManager(Realm.getDefaultInstance());
        responseList =recordManager.contains(charText);
        searchableItems.clear();
        searchableItems.addAll(responseList);
        notifyDataSetChanged();
    }

    private void webFilter(String charText) {
        SearchableService service  = ApiUtils.Companion.getSearchableService();
        charText.replace(" ","-");
        service.getItems(charText).enqueue(new Callback<List<RecordResponse>>() {
            @Override
            public void onResponse(Call<List<RecordResponse>> call, Response<List<RecordResponse>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                final List<RecordResponse> records = response.body();
                searchableItems.addAll(records);
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<RecordResponse>> call, Throwable t) {
                Log.v("onFailure:", "onFailure");
            }
        });

    }

    public void clear() {
        searchableItems.removeAll(responseList);
        searchableItems.clear();
        notifyDataSetChanged();
    }

}