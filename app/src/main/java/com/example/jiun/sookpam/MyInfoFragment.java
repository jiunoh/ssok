package com.example.jiun.sookpam;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyInfoFragment extends Fragment {

    public static MyInfoFragment newInstance() {
        MyInfoFragment fragment = new MyInfoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public MyInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_info, container, false);

        Spinner grade = (Spinner) view.findViewById(R.id.spinner_grade);
        ArrayAdapter spinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.grade, R.layout.support_simple_spinner_dropdown_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        grade.setAdapter(spinnerAdapter);



        return view;
    }

}
