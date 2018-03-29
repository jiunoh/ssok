package com.example.jiun.ssok.message;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.jiun.ssok.R;
import com.example.jiun.ssok.model.ContactDBManager;
import com.example.jiun.ssok.model.RecordDBManager;
import com.example.jiun.ssok.model.vo.RecordVO;
import com.example.jiun.ssok.user.major.MajorList;
import com.example.jiun.ssok.util.MsgContentGenerator;
import com.example.jiun.ssok.util.SharedPreferenceUtil;

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
                MsgContentGenerator.showMessageBody(getContext(), data);
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
}
