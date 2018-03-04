package com.example.jiun.sookpam.clip;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.jiun.sookpam.R;
import com.example.jiun.sookpam.clip.ClipFragment.OnListFragmentInteractionListener;
import com.example.jiun.sookpam.clip.ClipContent.ClipItem;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ClipItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ClipItemRecyclerViewAdapter extends RecyclerView.Adapter<ClipItemRecyclerViewAdapter.ViewHolder> {

    private final List<ClipItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public ClipItemRecyclerViewAdapter(List<ClipItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
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
        holder.titleView.setText(mValues.get(position).id);
        holder.categoryView.setText(mValues.get(position).content);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
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
