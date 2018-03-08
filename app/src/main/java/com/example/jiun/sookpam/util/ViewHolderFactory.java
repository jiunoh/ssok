package com.example.jiun.sookpam.util;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.jiun.sookpam.R;
import com.example.jiun.sookpam.clip.ClipItem;
import com.example.jiun.sookpam.searchable.DualModel;


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

    public static class ClipHolder extends RecyclerView.ViewHolder {
        public final View view;
        public TextView categoryTextView;
        public TextView titleTextVIew;
        public ImageView starView;
        public ClipItem item;

        public ClipHolder(View itemView) {
            super(itemView);
            view = itemView;
            categoryTextView = itemView.findViewById(R.id.category_view);
            titleTextVIew = itemView.findViewById(R.id.title_view);
            starView = itemView.findViewById(R.id.item_star);
        }

    }

    public static RecyclerView.ViewHolder create(ViewGroup parent, int viewType) {
        if (viewType == DualModel.RECORD_RESPONSE || viewType == DualModel.RECORD_VO) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchable_recycler_item, parent, false);
            return new SearchHolder(view);
        }
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_clip_item_list, parent, false);
            return new ClipHolder(view);
        }
    }
}
