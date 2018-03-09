package com.example.jiun.sookpam.searchable;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import com.example.jiun.sookpam.model.DualModel;
import com.example.jiun.sookpam.model.RecordDBManager;
import com.example.jiun.sookpam.server.ApiUtils;
import com.example.jiun.sookpam.server.RecordResponse;
import com.example.jiun.sookpam.server.SearchableService;
import com.example.jiun.sookpam.server.WebFilter;
import com.example.jiun.sookpam.util.ViewHolderFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchableRecyclerAdapter extends RecyclerView.Adapter {
    private ArrayList<DualModel> itemList;
    private ArrayList<? extends DualModel> responseList;

    public SearchableRecyclerAdapter(ArrayList<DualModel> items) {
        itemList = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ViewHolderFactory.create(parent, ViewHolderFactory.SEARCH_HOLDER);
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
        itemList.addAll(webFilter(charText));
        notifyDataSetChanged();
        realmFilter(charText);
        notifyDataSetChanged();
        return itemList.size() == 0;
    }

    public static ArrayList<DualModel> webFilter(String charText) {
        SearchableService service = ApiUtils.Companion.getSearchableService();
        final ArrayList<DualModel> itemList = new ArrayList<DualModel>();
        charText.replace(" ", "-");

        service.getItems(charText).enqueue(new Callback<List<RecordResponse>>() {
            @Override
            public void onResponse(Call<List<RecordResponse>> call, Response<List<RecordResponse>> response) {
                if (!response.isSuccessful())
                    return;
                final List<RecordResponse> records = response.body();
                itemList.addAll(records);
            }

            @Override
            public void onFailure(Call<List<RecordResponse>> call, Throwable t) {
                Log.v("onFailure:", "onFailure");
            }
        });

        return itemList;
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