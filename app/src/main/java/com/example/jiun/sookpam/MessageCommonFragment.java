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

        TextView category_janghak = view.findViewById(R.id.category_janghak);
        TextView category_haksa = view.findViewById(R.id.category_haksa);
        TextView category_iphak = view.findViewById(R.id.category_iphak);
        TextView category_mojip = view.findViewById(R.id.category_mojip);
        TextView category_it = view.findViewById(R.id.category_it);
        TextView category_gookje = view.findViewById(R.id.category_gookje);
        TextView category_chuiup = view.findViewById(R.id.category_chuiup);

        category_janghak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        category_haksa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        category_iphak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        category_mojip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        category_it.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        category_gookje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        category_chuiup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        category_janghak.setText("장학");
        category_janghak.setBackgroundResource(R.drawable.category_shape);

        return view;
    }

    public static MessageCommonFragment newInstance() {
        MessageCommonFragment fragment = new MessageCommonFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

}
