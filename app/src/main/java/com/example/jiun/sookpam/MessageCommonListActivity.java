package com.example.jiun.sookpam;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jiun.sookpam.model.ContactDBManager;
import com.example.jiun.sookpam.model.RecordDBManager;
import com.example.jiun.sookpam.model.vo.RecordVO;
import com.example.jiun.sookpam.util.MsgContentGenerator;
import com.example.jiun.sookpam.util.SharedPreferenceUtil;

import java.util.ArrayList;

import io.realm.Realm;

public class MessageCommonListActivity extends AppCompatActivity {
    private RecordDBManager categoryManager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_common_list);

        Intent intent = getIntent();
        String category = intent.getStringExtra("category");
        setToolbar(category);

        MessageDepartListAdapter adapter = new MessageDepartListAdapter();
        ListView listView = findViewById(R.id.message_common_listview);
        listView.setAdapter(adapter);

        final ArrayList<RecordVO> datalist = new ArrayList<RecordVO>();
        ContactDBManager contactDBManager = (ContactDBManager) this.getApplicationContext();
        ArrayList<String> divisionList = contactDBManager.getDivisionList(category, Realm.getDefaultInstance());

        for (String division : divisionList)
            datalist.addAll(getDataByDivision(division));

        adapter.addItem(datalist);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RecordVO data = datalist.get(position);
                MsgContentGenerator.showMessageBody(getApplicationContext(), data);
            }
        });

    }

    private void setToolbar(String category) {
        toolbar = (Toolbar) findViewById(R.id.list_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        toolbar.setTitle("문자 > " + category);
        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private ArrayList<RecordVO> getDataByDivision(String division) {
        categoryManager = new RecordDBManager(Realm.getDefaultInstance());
        ArrayList<RecordVO> response;
        if (!division.equals("기타"))
            response = categoryManager.getDataByDivision(division);
        else
            response = handleUnclipedCategories();

        return response;
    }

    private ArrayList<RecordVO> handleUnclipedCategories() {
        ContactDBManager contactDBManager = (ContactDBManager) getApplicationContext();
        ArrayList<String> divisionList = contactDBManager.getDepartmentList();
        ArrayList<RecordVO> response = new ArrayList<>();
        for (String division : divisionList) {
            if (!SharedPreferenceUtil.get(this, division, false))
                response.addAll(categoryManager.getDataByDivision(division));
        }
        return response;
    }

    public static class MessageCommonListAdapter extends BaseAdapter {
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
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.message_depart_item, parent, false);
                holder.category = (TextView) convertView.findViewById(R.id.message_depart_category);
                holder.title = (TextView) convertView.findViewById(R.id.message_depart_title);
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
}