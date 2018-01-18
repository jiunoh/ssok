package com.example.jiun.sookpam;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class DataRecyclerAdapter extends RecyclerView.Adapter<DataRecyclerAdapter.DataViewHolder> {
    private ArrayList<DataItem> dataItems = new ArrayList<DataItem>();

    DataRecyclerAdapter(ArrayList<DataItem> items) {
        dataItems = items;
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView body;

        public DataViewHolder(View view) {
            super(view);
            title = (TextView) itemView.findViewById(R.id.message_title);
            body = (TextView) itemView.findViewById(R.id.message_body);
        }
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_recycler_item, parent, false);
        DataViewHolder dataViewHolder = new DataViewHolder(v);
        return dataViewHolder;
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        holder.title.setText(dataItems.get(position).getTitle());
        holder.body.setText(dataItems.get(position).getBody());
    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

}