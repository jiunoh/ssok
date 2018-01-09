package com.example.jiun.sookpam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainListviewAdapter extends ArrayAdapter implements View.OnClickListener {

    public interface ListClickListener {
        void onCategoryButtonClicked();
    }

    private int resourceId;
    private ListClickListener listClickListener;

    MainListviewAdapter(Context context, int resource, ArrayList<CategoryListItem> list, ListClickListener listClickListener) {
        super(context, resource, list);
        this.resourceId = resource;
        this.listClickListener = listClickListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position ;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(this.resourceId, parent, false);
        }

        final TextView category = (TextView) convertView.findViewById(R.id.main_list_item);
        final CategoryListItem mainListviewItem = (CategoryListItem) getItem(position);

        // 아이템 내 각 위젯에 데이터 반영
        category.setText(mainListviewItem.getCategory());

        Button button = (Button) convertView.findViewById(R.id.main_button);
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // * * *
                //
                // * * *
            }
        });

        return convertView;
    }


    public void onClick(View v) {
        // * * *
        //
        // * * *
    }

}
