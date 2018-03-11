package com.example.jiun.sookpam.searchable;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.example.jiun.sookpam.model.DualModel;
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
    private ArrayList<DualModel> dualList;
    private ArrayList<? extends DualModel> responseList;

    SearchableRecyclerAdapter(ArrayList<DualModel> items) {
        dualList = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ViewHolderFactory.create(parent, ViewHolderFactory.SEARCH_HOLDER);
    }

    @Override
    public int getItemViewType(int position) {
        return dualList.get(position).getItemViewType();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        dualList.get(position).onBindViewHolder(holder);
    }


    @Override
    public int getItemCount() {
        return dualList.size();
    }

    public boolean filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        dualList.clear();
        realmFilter(charText);
        webFilter(charText);
        return dualList.size() == 0;
    }

    private void realmFilter(String charText) {
        RecordDBManager recordManager = new RecordDBManager(Realm.getDefaultInstance());
        responseList =recordManager.contains(charText);
        dualList.addAll(responseList);
        notifyDataSetChanged();
    }

    private void webFilter(String charText) {
        SearchableService service  = ApiUtils.Companion.getSearchableService();
        charText = charText.replaceAll("\\s+" ,"-");
        service.getItems(charText).enqueue(new Callback<List<RecordResponse>>() {
            @Override
            public void onResponse(Call<List<RecordResponse>> call, Response<List<RecordResponse>> response) {
                if (!response.isSuccessful()) {
                    Log.v("response", " disconnected");
                    return;
                }
                final List<RecordResponse> records = response.body();
                for (RecordResponse record : records) {
                    Log.v("record in records : ",record.toString());
                    dualList.add(record);
                }
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<RecordResponse>> call, Throwable t) {
                Log.v("onFailure:", "onFailure");
            }
        });

    }

    public void clear() {
        dualList.removeAll(responseList);
        dualList.clear();
        notifyDataSetChanged();
    }

}