package com.example.jiun.sookpam.searchable;

import android.icu.text.AlphabeticIndex;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.jiun.sookpam.R;
import com.example.jiun.sookpam.model.RecordDBManager;
import com.example.jiun.sookpam.model.vo.RecordVO;
import java.util.ArrayList;
import java.util.Locale;

import io.realm.Realm;

public class SearchableRecyclerAdapter extends RecyclerView.Adapter<SearchableRecyclerAdapter.RecordViewHolder> {
    private ArrayList<RecordVO> searchableItems = new ArrayList<RecordVO>();
    private ArrayList<RecordVO> responseList;

    SearchableRecyclerAdapter(ArrayList<RecordVO> items) {
        searchableItems = items;
    }

    public static class RecordViewHolder extends RecyclerView.ViewHolder {
        TextView category;
        TextView division;
        TextView title;

        public RecordViewHolder(View view) {
            super(view);
            category = (TextView) itemView.findViewById(R.id.category_view);
            division = (TextView) itemView.findViewById(R.id.division_view);
            title = (TextView) itemView.findViewById(R.id.title_view);
        }
    }

    @Override
    public RecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchable_recycler_item, parent, false);
        RecordViewHolder recordViewHolder = new RecordViewHolder(v);
        return recordViewHolder;
    }

    @Override
    public void onBindViewHolder(RecordViewHolder holder, int position) {
        holder.category.setText(searchableItems.get(position).getCategory());
        holder.division.setText(searchableItems.get(position).getDivision());
        String body = searchableItems.get(position).getMessage().getBody();
        body = body.replaceFirst("\\[Web발신\\]\n", "");
        String title = body.split("\n")[0];
        holder.title.setText(title);
    }

    @Override
    public int getItemCount() {
        return searchableItems.size();
    }

    // Filter Class
    public void filter(String charText) {
        Log.v("filter: ", charText);
        charText = charText.toLowerCase(Locale.getDefault());
        responseList = getQuery(charText);
        searchableItems.clear();
        searchableItems.addAll(responseList);
        if (responseList.size() != 0)
            Log.v("filter_last_item: ", searchableItems.get(0).getDivision());
        notifyDataSetChanged();
    }

    public void clear() {
        searchableItems.removeAll(responseList);
        notifyDataSetChanged();
    }


    private  ArrayList<RecordVO> getQuery(String query){
        RecordDBManager recordManager = new RecordDBManager(Realm.getDefaultInstance());
        ArrayList<RecordVO> contains = recordManager.contains(query);
        return contains;
    }
}