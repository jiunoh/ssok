package com.example.jiun.sookpam.util;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jiun.sookpam.R;
import com.example.jiun.sookpam.searchable.SearchItem;


public class ViewHolderFactory {

    public static class SearchHolder extends RecyclerView.ViewHolder {
        public TextView categoryTextView;
        public TextView titleTextVIew;

        public SearchHolder(View itemView) {
            super(itemView);
            categoryTextView = itemView.findViewById(R.id.category_view);
            titleTextVIew = itemView.findViewById(R.id.title_view);
        }

    }


    public static RecyclerView.ViewHolder create(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchable_recycler_item, parent, false);
        return new SearchHolder(view);
    }
}
