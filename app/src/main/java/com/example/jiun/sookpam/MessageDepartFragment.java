package com.example.jiun.sookpam;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.jiun.sookpam.message.ContentActivity;
import com.example.jiun.sookpam.message.ContentItem;
import com.example.jiun.sookpam.model.ContactDBManager;
import com.example.jiun.sookpam.model.RecordDBManager;
import com.example.jiun.sookpam.model.vo.RecordVO;
import com.example.jiun.sookpam.user.major.MajorList;
import com.example.jiun.sookpam.util.SharedPreferenceUtil;

import java.util.ArrayList;

import io.realm.Realm;


public class MessageDepartFragment extends Fragment {
    private RecordDBManager categoryManager;

    public MessageDepartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_depart, container, false);
        MessageDepartListAdapter adapter = new MessageDepartListAdapter();
        ListView listView = view.findViewById(R.id.message_depart_listview);
        listView.setAdapter(adapter);

        final ArrayList<RecordVO> datalist = new ArrayList<RecordVO>();

        ArrayList<ArrayList<String>> collegeAndMajors = MajorList.Companion.getCollegeAndMajors();
        for (ArrayList<String> college: collegeAndMajors) {
            for (String major: college) {
                boolean isSelected = SharedPreferenceUtil.get(getContext(), major, false);
                if (isSelected) {
                    datalist.addAll(getDataByDivision(major));
                    adapter.addItem(datalist);
                }
            }
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RecordVO data = datalist.get(position);
                showMessageBody(data);
            }
        });

        return view;
    }

    private ArrayList<RecordVO> getDataByDivision(String division) {
        categoryManager = new RecordDBManager(Realm.getDefaultInstance());
        ArrayList<RecordVO> response;
        if (!division.equals("공지"))
            response = categoryManager.getDataByDivision(division);
        else
            response = handleUnclipedCategories();

        return response;
    }

    private ArrayList<RecordVO> handleUnclipedCategories() {
        ContactDBManager contactDBManager =  (ContactDBManager)getActivity().getApplicationContext();
        ArrayList<String> divisionList = contactDBManager.getDepartmentList();
        ArrayList<RecordVO> response = new ArrayList<>();
        for (String division : divisionList) {
            if (!SharedPreferenceUtil.get(getContext(), division, false))
                response.addAll(categoryManager.getDataByDivision(division));
        }
        return response;
    }

    public static MessageDepartFragment newInstance() {
        MessageDepartFragment fragment = new MessageDepartFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private void showMessageBody(RecordVO data) {
        Intent intent = new Intent(getContext(), ContentActivity.class);
        ContentItem contentItem  = new ContentItem();
        contentItem.setCategory(data.getCategory());
        contentItem.setDivision(data.getDivision());
        contentItem.setBody(data.getMessage().getBody());
        contentItem.setPhone(data.getMessage().getPhoneNumber());
        Bundle bundle = new Bundle();
        bundle.putSerializable("OBJECT", contentItem);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
