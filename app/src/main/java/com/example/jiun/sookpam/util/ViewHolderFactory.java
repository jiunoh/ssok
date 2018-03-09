package com.example.jiun.sookpam.util;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.jiun.sookpam.R;


public class ViewHolderFactory {
    public static int SEARCH_HOLDER = 0;
    public static int CLIP_HOLDER = 1;

    public static class DualHolder extends RecyclerView.ViewHolder {
        public TextView categoryTextView;
        public TextView titleTextVIew;

        public DualHolder(View itemView) {
            super(itemView);
            categoryTextView = itemView.findViewById(R.id.category_view);
            titleTextVIew = itemView.findViewById(R.id.title_view);
        }

    }

    public static class ClipHolder extends DualHolder {
        public ImageView starView;

        public ClipHolder(View itemView) {
            super(itemView);
            categoryTextView = itemView.findViewById(R.id.clip_category);
            titleTextVIew = itemView.findViewById(R.id.clip_title);
            starView = itemView.findViewById(R.id.clip_star);
        }
    }

    public static RecyclerView.ViewHolder create(ViewGroup parent, int holderType) {
        if (holderType == SEARCH_HOLDER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchable_recycler_item, parent, false);
            return new DualHolder(view);
        }
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_clip_item_list, parent, false);
            return new ClipHolder(view);
        }
    }
}
