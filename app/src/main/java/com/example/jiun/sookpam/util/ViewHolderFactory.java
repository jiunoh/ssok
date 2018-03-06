package com.example.jiun.sookpam.util;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.jiun.sookpam.R;
import com.example.jiun.sookpam.searchable.SearchItem;


public class ViewHolderFactory {

    public static class RealmHolder extends RecyclerView.ViewHolder {
        public TextView category;
        public TextView division;
        public TextView title;

        public RealmHolder(View itemView) {
            super(itemView);
            category = (TextView) itemView.findViewById(R.id.category_view);
            division = (TextView) itemView.findViewById(R.id.division_view);
            title = (TextView) itemView.findViewById(R.id.title_view);
        }

    }

    public static class WebHolder extends RecyclerView.ViewHolder {
        public TextView categoryTextView;
        public TextView titleTextVIew;

        public WebHolder(View itemView) {
            super(itemView);
            categoryTextView = (TextView) itemView.findViewById(R.id.web_recom_category);
            titleTextVIew = (TextView) itemView.findViewById(R.id.web_recom_title);
        }
    }

    public static RecyclerView.ViewHolder create(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case SearchItem.RECORD_VO:
                view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchable_recycler_item, parent, false);
                return new RealmHolder(view );

            case SearchItem.RECORD_RESPONSE:
                view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.web_recom_item, parent, false);
                return new WebHolder(view );

            default:
                return null;
        }

    }
}
