package com.example.jiun.sookpam.searchable;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.jiun.sookpam.R;
import com.example.jiun.sookpam.model.vo.RecordVO;
import java.util.ArrayList;

public class SearchableRecyclerAdapter extends RecyclerView.Adapter<SearchableRecyclerAdapter.RecordViewHolder> {
    private ArrayList<RecordVO> dataItems = new ArrayList<RecordVO>();

    SearchableRecyclerAdapter(ArrayList<RecordVO> items) {
        dataItems = items;
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
        holder.category.setText(dataItems.get(position).getCategory());
        holder.division.setText(dataItems.get(position).getDivision());
        String body = dataItems.get(position).getMessage().getBody();
        body = body.replaceFirst("\\[Web발신\\]\n", "");
        String title = body.split("\n")[0];
        holder.title.setText(title);
    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

}