package com.example.jiun.sookpam;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class WebRecomFragment extends Fragment {

    public static WebRecomFragment newInstance() {
        WebRecomFragment fragment = new WebRecomFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public WebRecomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web_recom, container, false);
        MessageDepartListAdapter adapter = new MessageDepartListAdapter();
        ListView listView = view.findViewById(R.id.web_recom_listview);
        listView.setAdapter(adapter);

        adapter.addItem("demo web title", "demo");
        adapter.addItem("demo web title", "demo");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity().getApplicationContext(), "리스트 아이템 클릭됨", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

}
