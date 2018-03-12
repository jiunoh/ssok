package com.example.jiun.sookpam.searchable;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.example.jiun.sookpam.model.DualModel;
import com.example.jiun.sookpam.model.RecordDBManager;
import com.example.jiun.sookpam.util.ViewHolderFactory;
import java.util.ArrayList;
import java.util.List;
import io.realm.Realm;

public class SearchableRecyclerAdapter extends RecyclerView.Adapter {
    private List<DualModel> dualList;
    private ArrayList<? extends DualModel> recordVoList;

    SearchableRecyclerAdapter(List<DualModel> items) {
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

    public List<DualModel> searchInRealm(String charText) {
        RecordDBManager recordManager = new RecordDBManager(Realm.getDefaultInstance());
        recordVoList = recordManager.contains(charText);
        dualList.addAll(recordVoList);
        notifyDataSetChanged();
        return dualList;
    }

    public void clear() {
        dualList.removeAll(recordVoList);
        dualList.clear();
        notifyDataSetChanged();
    }

}