package com.example.jiun.sookpam;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WebCommonFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class WebCommonFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public WebCommonFragment() {
        // Required empty public constructor
    }

    public static WebCommonFragment newInstance() {
        WebCommonFragment fragment = new WebCommonFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web_common, container, false);

        TextView category_janghak = view.findViewById(R.id.category_janghak);
        TextView category_haksa = view.findViewById(R.id.category_haksa);
        TextView category_iphak = view.findViewById(R.id.category_iphak);
        TextView category_mojip = view.findViewById(R.id.category_mojip);
        TextView category_it = view.findViewById(R.id.category_it);
        TextView category_gookje = view.findViewById(R.id.category_gookje);
        TextView category_chuiup = view.findViewById(R.id.category_chuiup);

        category_janghak.setText("장학");
        category_janghak.setBackgroundResource(R.drawable.category_shape);

        return view;
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
