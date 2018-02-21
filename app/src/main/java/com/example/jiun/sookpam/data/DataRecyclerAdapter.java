package com.example.jiun.sookpam.data;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jiun.sookpam.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DataRecyclerAdapter extends RecyclerView.Adapter<DataRecyclerAdapter.DataViewHolder> {
    private ArrayList<DataItem> dataItems = new ArrayList<DataItem>();

    DataRecyclerAdapter(ArrayList<DataItem> items) {
        dataItems = items;
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView date;

        public DataViewHolder(View view) {
            super(view);
            title = (TextView) itemView.findViewById(R.id.message_title);
            date = (TextView) itemView.findViewById(R.id.date_view);
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
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = formatter.format(dataItems.get(position).getDate());
        holder.date.setText(formattedDate);
    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

}