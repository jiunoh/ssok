package com.example.jiun.sookpam.searchable;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.jiun.sookpam.R;
import com.example.jiun.sookpam.model.RecordDBManager;
import com.example.jiun.sookpam.model.vo.RecordVO;
import com.example.jiun.sookpam.server.RecordRecyclerAdapter;
import java.util.ArrayList;
import java.util.Locale;
import io.realm.Realm;

public class SearchableRecyclerAdapter extends RecyclerView.Adapter {
    private ArrayList<SearchItem> searchableItems = new ArrayList<SearchItem>();
    private ArrayList<? extends SearchItem> responseList;

    SearchableRecyclerAdapter(ArrayList<SearchItem> items) {
        searchableItems = items;
    }

    public static class RecordViewHolder extends RecyclerView.ViewHolder {
        TextView category;
        TextView division;
        TextView title;

        public RecordViewHolder(View view) {
            super(view);
            category = (TextView)  view.findViewById(R.id.category_view);
            division = (TextView)  view.findViewById(R.id.division_view);
            title = (TextView)  view.findViewById(R.id.title_view);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == 1) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchable_recycler_item, parent, false);
            RecordViewHolder recordViewHolder = new RecordViewHolder(v);
            return recordViewHolder;
        }
      else if (viewType == 2) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.web_recom_item, parent, false);
            RecordRecyclerAdapter.ViewHolder recordViewHolder = new RecordRecyclerAdapter.ViewHolder(v);
            return recordViewHolder;
        }
        else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RecordViewHolder) {
            ((RecordViewHolder)holder).category.setText(((RecordVO)searchableItems.get(position)).getCategory());
            ((RecordViewHolder)holder).division.setText(((RecordVO)searchableItems.get(position)).getDivision());
            String body = ((RecordVO)searchableItems.get(position)).getMessage().getBody();
            body = body.replaceFirst("\\[Web발신\\]\n", "");
            String title = body.split("\n")[0];
            ((RecordViewHolder)holder).title.setText(title);
        } else if (holder instanceof RecordRecyclerAdapter.ViewHolder) {
            //((RecordRecyclerAdapter.ViewHolder) holder).textView2.setText(((RecordRecyclerAdapter.ViewHolder) dataSet.get(position)).getText());
        }
    }


    @Override
    public int getItemCount() {
        return searchableItems.size();
    }


    public void filter(String charText) {
        Log.v("filter: ", charText);
        charText = charText.toLowerCase(Locale.getDefault());
        responseList = getQuery(charText) ;
        searchableItems.clear();
        searchableItems.addAll(responseList);
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