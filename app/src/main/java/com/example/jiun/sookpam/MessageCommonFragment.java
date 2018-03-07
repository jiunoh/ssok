package com.example.jiun.sookpam;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.example.jiun.sookpam.user.setting.SettingCategory;
import com.example.jiun.sookpam.util.SharedPreferenceUtil;
import com.example.jiun.sookpam.message.MessageContract;
import com.example.jiun.sookpam.message.MessagePresenter;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import org.jetbrains.annotations.NotNull;

public class MessageCommonFragment extends Fragment implements MessageContract.View {
    Context context;
    Activity activity;
    MessageContract.Presenter presenter;
    ImageButton refreshImageButton;
    LoadingDialog loadingDialog;
    TextView[] category_textviews;
    final String[] categories = {"장학", "학사", "입학", "모집", "시스템", "국제", "취업", "학생"};


    public MessageCommonFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_common, container, false);
        context = view.getContext();
        activity = getActivity();
        category_textviews = new TextView[7];
        initialize();

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

    private void initialize() {
        loadingDialog = new LoadingDialog(context);
        setPresenter(new MessagePresenter(context.getApplicationContext(), MessageCommonFragment
                .this, loadingDialog));
        refreshImageButton = activity.findViewById(R.id.main_refresh_image_btn);
        refreshImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation rotateAnimation = UIAnimation.Companion.setRotateAnimation(refreshImageButton);
                refreshImageButton.startAnimation(rotateAnimation);
                presenter.start();
            }
        });
        presenter.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.cancelMessageAsyncTask();
    }

    @Override
    public void showPermissionMessage(@NotNull PermissionListener permissionListener) {
        TedPermission.with(context)
                .setPermissionListener(permissionListener)
                .setRationaleTitle(getString(R.string.read_sms_request_title))
                .setRationaleMessage(getString(R.string.read_sms_request_detail))
                .setDeniedTitle(getString(R.string.denied_read_sms_title))
                .setDeniedMessage(getString(R.string.denied_read_sms_detail))
                .setGotoSettingButtonText(getString(R.string.move_setting))
                .setPermissions(android.Manifest.permission.READ_SMS)
                .check();
    }

    @Override
    public void showToastMessage(@NotNull String string, int toastTime) {
        Toast.makeText(getContext(), string, toastTime).show();
    }

    @Override
    public MessageContract.Presenter getPresenter() {
        return presenter;
    }

    @Override
    public void setPresenter(MessageContract.Presenter presenter) {
        this.presenter = presenter;
    }

    private void goToListPage(int i) {
        Toast.makeText(getActivity().getApplicationContext(), "클릭됨", Toast.LENGTH_SHORT).show();
//        putExtra(categories[i]);
    }

    public static MessageCommonFragment newInstance() {
        MessageCommonFragment fragment = new MessageCommonFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
