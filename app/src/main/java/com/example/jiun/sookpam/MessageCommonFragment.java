package com.example.jiun.sookpam;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiun.sookpam.user.PersonalCategory;
import com.example.jiun.sookpam.util.SharedPreferenceUtil;

public class MessageCommonFragment extends Fragment {
    TextView category_janghak, category_haksa, category_iphak, category_mojip, category_it, category_gookje, category_chuiup;
    TextView[] category_textviews;
    final int NORMAL_CATEGORY = 0;
    final int INTEREST_CATEGORY = 1;
    final int UNINTEREST_CATEGORY = 2;
    final int PAGE3 = 2;
    final int PAGE4 = 3;
    final String[] categories = {"장학", "학사", "입학", "모집", "시스템", "국제", "취업"};

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
        Toast.makeText(getActivity().getApplicationContext(), "클릭됨", Toast.LENGTH_LONG).show();
//        putExtra(categories[i]);
    }

    public static MessageCommonFragment newInstance() {
        MessageCommonFragment fragment = new MessageCommonFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

}
