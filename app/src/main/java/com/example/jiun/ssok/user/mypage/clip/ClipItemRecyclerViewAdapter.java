package com.example.jiun.ssok.user.mypage.clip;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.example.jiun.ssok.model.DualModel;
import com.example.jiun.ssok.util.ViewHolderFactory;
import java.util.List;

public class ClipItemRecyclerViewAdapter extends RecyclerView.Adapter {
    public List<DualModel> modelList;
    public ClipItemRecyclerViewAdapter(List<DualModel> items) {
        modelList = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ViewHolderFactory.create(parent);
    }

    @Override
    public int getItemViewType(int position) {
        return modelList.get(position).getItemViewType();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        modelList.get(position).onBindViewHolder(holder);
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
 }
