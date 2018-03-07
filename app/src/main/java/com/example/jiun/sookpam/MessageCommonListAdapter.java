package com.example.jiun.sookpam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jiun.sookpam.model.vo.RecordVO;

import java.util.ArrayList;

public class MessageCommonListAdapter extends BaseAdapter {
    private ArrayList<RecordVO> messageCommonItems = new ArrayList<RecordVO>();

    public static class MessageCommonViewHolder {
        TextView category;
        TextView title;
    }

    @Override
    public int getCount() {
        return messageCommonItems.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();
        MessageCommonListAdapter.MessageCommonViewHolder holder;
        holder = new MessageCommonListAdapter.MessageCommonViewHolder();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.message_depart_item, parent, false);
            holder.category = (TextView) convertView.findViewById(R.id.message_depart_category);
            holder.title = (TextView) convertView.findViewById(R.id.message_depart_title);
            convertView.setTag(holder);
        } else
            holder = (MessageCommonListAdapter.MessageCommonViewHolder) convertView.getTag();

        RecordVO messageDepartItem = messageCommonItems.get(position);

        String messageBody = messageDepartItem.getMessage().getBody();
        if (messageBody.contains("[Web발신]"))
            messageBody = messageBody.replace("[Web발신]", "");

        messageBody = messageBody.replaceAll("\n", " ");
        messageBody = messageBody.replaceFirst(" ", "");

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
        return messageCommonItems.get(position);
    }

    public void addItem(ArrayList<RecordVO> itemList) {
        messageCommonItems.addAll(itemList);
    }

    public void clear() {
        messageCommonItems.clear();
    }
}
