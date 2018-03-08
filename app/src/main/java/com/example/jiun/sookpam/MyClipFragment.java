package com.example.jiun.sookpam;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import com.example.jiun.sookpam.clip.ClipItemRecyclerViewAdapter;
import com.example.jiun.sookpam.searchable.DualModel;

import java.util.ArrayList;

public class MyClipFragment extends Fragment {
    private MyClipFragment.OnListFragmentInteractionListener mListener;
    private ArrayList<DualModel> responseList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        responseList = new ArrayList<DualModel>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_my_clip, container, false);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setAdapter(new ClipItemRecyclerViewAdapter(responseList));
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MyClipFragment.OnListFragmentInteractionListener) {
            mListener = (MyClipFragment.OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(ClipItem item);
    }
}
