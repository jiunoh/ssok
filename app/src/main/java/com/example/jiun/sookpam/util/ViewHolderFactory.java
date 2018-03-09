package com.example.jiun.sookpam.util;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.jiun.sookpam.R;
import com.example.jiun.sookpam.model.DualModel;


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
        public TextView categoryTextView;
        public TextView titleTextVIew;
        public ImageView starView;

        public ClipHolder(View itemView) {
            super(itemView);
            categoryTextView = itemView.findViewById(R.id.clip_category);
            titleTextVIew = itemView.findViewById(R.id.clip_title);
            starView = itemView.findViewById(R.id.clip_star);
            starView.setImageResource(R.drawable.star_on);
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
