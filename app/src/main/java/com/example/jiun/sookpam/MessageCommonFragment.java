package com.example.jiun.sookpam;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class MessageCommonFragment extends Fragment {

    public MessageCommonFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_common, container, false);

        TextView category1 = view.findViewById(R.id.category_1);
        TextView category1_1 = view.findViewById(R.id.category_1_content1);
        TextView category1_2 = view.findViewById(R.id.category_1_content2);
        TextView category1_3 = view.findViewById(R.id.category_1_content3);
        TextView button1 = view.findViewById(R.id.category_1_button);

        category1.setText("장학");
        category1_1.setText("아산장학재단 신청 안내");
        category1_2.setText("근로장학생 모집");
        category1_3.setText("외부장학금 안내");
        button1.setText("MORE >");

        // Inflate the layout for this fragment
        return view;
    }

    public static MessageCommonFragment newInstance() {
        MessageCommonFragment fragment = new MessageCommonFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

}
