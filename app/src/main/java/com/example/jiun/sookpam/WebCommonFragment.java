package com.example.jiun.sookpam;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiun.sookpam.user.PersonalCategory;
import com.example.jiun.sookpam.util.SharedPreferenceUtil;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WebCommonFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class WebCommonFragment extends Fragment {
    TextView[] category_textviews;
    final int NORMAL_CATEGORY = 0;
    final String[] categories = {"장학", "학사", "입학", "모집", "시스템", "국제", "취업", "학생"};
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

        category_textviews = new TextView[7];

        int i, j = 0;
        for (i=0; i< categories.length; i++) {
            if (SharedPreferenceUtil.get(getContext(), categories[i], PersonalCategory.NORMAL_CATEGORY) == PersonalCategory.INTEREST_CATEGORY) {
                final int ii=i;
                String categoryID = "category_"+j;
                j++;
                int resID = getResources().getIdentifier(categoryID, "id", getActivity().getPackageName());
                category_textviews[j] = view.findViewById(resID);
                category_textviews[j].setText(categories[i]);
                category_textviews[j].setBackgroundResource(R.drawable.category_shape);
                category_textviews[j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goToListPage(ii);
                    }
                });
            }
        }

        return view;
    }

    private void goToListPage(int i) {
        Toast.makeText(getActivity().getApplicationContext(), "클릭됨", Toast.LENGTH_SHORT).show();
//        putExtra(categories[i]);
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
