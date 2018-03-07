package com.example.jiun.sookpam;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jiun.sookpam.model.ContactDBManager;
import com.example.jiun.sookpam.model.RecordDBManager;
import com.example.jiun.sookpam.model.vo.RecordVO;
import com.example.jiun.sookpam.user.major.MajorList;
import com.example.jiun.sookpam.util.SharedPreferenceUtil;

import java.util.ArrayList;

import io.realm.Realm;


public class MessageDepartFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
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

        ContactDBManager contactDBManager = (ContactDBManager)getActivity().getApplicationContext();
        ArrayList<RecordVO> datalist;

        ArrayList<ArrayList<String>> collegeAndMajors = MajorList.Companion.getCollegeAndMajors();
        for (ArrayList<String> college: collegeAndMajors) {
            for (String major: college) {
                boolean isSelected = SharedPreferenceUtil.get(getContext(), major, false);
                if (isSelected) {
                    datalist = getDataByDivision(major);
                    adapter.addItem(datalist);
                }
            }
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity().getApplicationContext(), "리스트 아이템 클릭됨", Toast.LENGTH_LONG).show();
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
