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
        category1.setText("장학");
        category1.setBackgroundResource(R.drawable.category_shape);

        TextView category2 = view.findViewById(R.id.category_2);
        category2.setText("학사");
        category2.setBackgroundResource(R.drawable.category_shape);

        return view;
    }

    public static MessageCommonFragment newInstance() {
        MessageCommonFragment fragment = new MessageCommonFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

}
