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

import com.example.jiun.sookpam.message.MessageContract;
import com.example.jiun.sookpam.message.MessagePresenter;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.realm.Realm;


public class MessageCommonFragment extends Fragment implements MessageContract.View {
    Context context;
    Activity activity;
    MessageContract.Presenter presenter;
    ImageButton refreshImageButton;
    LoadingDialog loadingDialog;

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

        initialize();

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

    public static MessageCommonFragment newInstance() {
        MessageCommonFragment fragment = new MessageCommonFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
