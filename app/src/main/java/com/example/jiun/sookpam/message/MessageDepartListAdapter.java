package com.example.jiun.sookpam.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jiun.sookpam.R;
import com.example.jiun.sookpam.model.vo.RecordVO;
import com.example.jiun.sookpam.util.DateFormatter;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MessageDepartListAdapter extends BaseAdapter {
    private ArrayList<RecordVO> messageDepartItems = new ArrayList<RecordVO>();

    public static class MessageDepartViewHolder {
        TextView category;
        TextView title;
        TextView date;
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
            convertView = inflater.inflate(R.layout.searchable_recycler_item, parent, false);
            holder.category = (TextView) convertView.findViewById(R.id.category_view);
            holder.title = (TextView) convertView.findViewById(R.id.title_view);
            holder.date = (TextView) convertView.findViewById(R.id.date_view);
            convertView.setTag(holder);
        } else
            holder = (MessageDepartViewHolder) convertView.getTag();

        RecordVO messageDepartItem = messageDepartItems.get(position);

        String messageBody = messageDepartItem.getMessage().getBody();
        if (messageBody.contains("[Web발신]"))
            messageBody = messageBody.replace("[Web발신]", "");

        messageBody = messageBody.replaceAll("\n", " ");
        messageBody = messageBody.replaceFirst(" ", "");

        holder.title.setText(messageBody);
        holder.category.setText(messageDepartItem.getDivision());
        String date = DateFormatter.getFormatted(messageDepartItem.getMessage().getDate());
        holder.date.setText(date);
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
