package com.example.jiun.sookpam;


import android.content.Intent;
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
import com.example.jiun.sookpam.user.PersonalCategory;
import com.example.jiun.sookpam.util.SharedPreferenceUtil;

import java.util.ArrayList;

import io.realm.Realm;


public class MessageCommonListFragment extends Fragment {
    private RecordDBManager categoryManager;
    private static final String DIVISION = "DVISION";
    private String division;

    public MessageCommonListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_common_list, container, false);

        MessageDepartListAdapter adapter = new MessageDepartListAdapter();
        ListView listView = view.findViewById(R.id.message_common_listview);
        listView.setAdapter(adapter);

        final ArrayList<RecordVO> datalist = new ArrayList<RecordVO>();
        ContactDBManager contactDBManager = (ContactDBManager)getActivity().getApplicationContext();
        ArrayList<String> departmentList = contactDBManager.getDepartmentList();
        String category = "";

        for (int i=0; i<departmentList.size(); i++) {
            division = departmentList.get(i);
            category = contactDBManager.getCategory(division, Realm.getDefaultInstance());
            if (SharedPreferenceUtil.get(getContext(), category, PersonalCategory.NORMAL_CATEGORY) == PersonalCategory.INTEREST_CATEGORY && category != "공통") {
                datalist.addAll(getDataByDivision(division));
                adapter.addItem(datalist);
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

    public static MessageCommonListFragment newInstance(String division) {
        MessageCommonListFragment fragment = new MessageCommonListFragment();
        Bundle args = new Bundle();
        args.putString(DIVISION, division);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            division = getArguments().getString(DIVISION);
        }
    }

}
