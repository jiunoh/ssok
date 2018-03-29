package com.example.jiun.ssok.util;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jiun.ssok.R;


public class ViewHolderFactory {
    public static class DualHolder extends RecyclerView.ViewHolder {
        public TextView categoryTextView;
        public TextView titleTextVIew;
        public TextView dateTextView;

        public DualHolder(View itemView) {
            super(itemView);
            categoryTextView = itemView.findViewById(R.id.category_view);
            titleTextVIew = itemView.findViewById(R.id.title_view);
            dateTextView = itemView.findViewById(R.id.date_view);
        }
    }


    public static RecyclerView.ViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchable_recycler_item, parent, false);
        return new DualHolder(view);
    }
}
