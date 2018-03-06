package com.example.jiun.sookpam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jiun.sookpam.model.vo.RecordVO;

import java.util.ArrayList;

public class MessageDepartListAdapter extends BaseAdapter {
    private ArrayList<RecordVO> messageDepartItems = new ArrayList<RecordVO>();

    public static class MessageDepartViewHolder {
        TextView category;
        TextView title;
    }

    @Override
    public int getCount() {
        return messageDepartItems.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();
        MessageDepartViewHolder holder;
        holder = new MessageDepartViewHolder();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.message_depart_item, parent, false);
            holder.category = (TextView) convertView.findViewById(R.id.message_depart_category);
            holder.title = (TextView) convertView.findViewById(R.id.message_depart_title);
            convertView.setTag(holder);
        } else
            holder = (MessageDepartViewHolder) convertView.getTag();

        RecordVO messageDepartItem = messageDepartItems.get(position);

        String messageBody = messageDepartItem.getMessage().getBody();
        if (messageBody.length() > 20)
            messageBody = messageBody.substring(0, 20);

        holder.title.setText(messageBody);
        holder.category.setText(messageDepartItem.getDivision());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return messageDepartItems.get(position);
    }

    public void addItem(ArrayList<RecordVO> itemList) {
        messageDepartItems.addAll(itemList);
    }

    public void clear() {
        messageDepartItems.clear();
    }
}
