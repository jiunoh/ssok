package com.example.jiun.sookpam.clip;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.jiun.sookpam.R;
import com.example.jiun.sookpam.clip.ClipContent.ClipItem;
import java.util.List;

public class ClipItemRecyclerViewAdapter extends RecyclerView.Adapter<ClipItemRecyclerViewAdapter.ViewHolder> {

    private final List<ClipItem> mValues;

    public ClipItemRecyclerViewAdapter(List<ClipItem> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_clip_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.titleView.setText(mValues.get(position).title);
        holder.categoryView.setText(mValues.get(position).category);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView titleView;
        public final TextView categoryView;
        public ImageView starView;
        public ClipItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            titleView = (TextView) view.findViewById(R.id.item_title);
            categoryView = (TextView) view.findViewById(R.id.category_view);
            starView = (ImageView)view.findViewById(R.id.item_star);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + categoryView.getText() + "'";
        }
    }
}
