package com.example.jiun.sookpam;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.jiun.sookpam.user.setting.SettingCategory;
import com.example.jiun.sookpam.util.SharedPreferenceUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class WebDepartFragment extends Fragment {
    TextView[] category_textviews;
    final String[] categories = {"장학", "학사", "입학", "모집", "시스템", "국제", "취업", "학생"};


    public static WebDepartFragment newInstance() {
        WebDepartFragment fragment = new WebDepartFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public WebDepartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web_depart, container, false);

        category_textviews = new TextView[7];

        int i, j = 0;
        for (i=0; i< categories.length; i++) {
            if (SharedPreferenceUtil.get(getContext(), categories[i], SettingCategory.NORMAL_CATEGORY) == SettingCategory.INTEREST_CATEGORY) {
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
}
