package com.example.jiun.sookpam.clip;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.example.jiun.sookpam.MyClipFragment.OnListFragmentInteractionListener;
import com.example.jiun.sookpam.searchable.DualModel;
import com.example.jiun.sookpam.searchable.SearchableRecyclerAdapter;
import com.example.jiun.sookpam.util.ViewHolderFactory;
import java.util.ArrayList;
import io.realm.Realm;

public class ClipItemRecyclerViewAdapter extends SearchableRecyclerAdapter {
    private ArrayList<DualModel> itemList;
    private final OnListFragmentInteractionListener listener;

    ClipItemRecyclerViewAdapter(ArrayList<DualModel> items, OnListFragmentInteractionListener listener) {
        super(items);
        itemList = items;
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    ClipItem item = ((ViewHolderFactory.ClipHolder)holder).item;
                    listener.onListFragmentInteraction(item);
                }
            }
        });
    }

    private void dataLoad() {
        ClipDBManager dbManager = new ClipDBManager(Realm.getDefaultInstance());
        itemList = dbManager.select();
    }
 }
