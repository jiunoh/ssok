package com.example.jiun.sookpam;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.*;
import android.view.*;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.jiun.sookpam.message.ContentActivity;
import com.example.jiun.sookpam.message.MessageContract;
import com.example.jiun.sookpam.message.MessagePresenter;
import com.example.jiun.sookpam.user.setting.SettingCategory;
import com.example.jiun.sookpam.util.SharedPreferenceUtil;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class MessageCommonFragment extends Fragment implements MessageContract.View {
    Context context;
    Activity activity;
    View view;
    MessageContract.Presenter presenter;
    ImageButton refreshImageButton;
    LoadingDialog loadingDialog;
    ProgressBar progressbar;
    RecyclerView messageCommonRecyclerView;
    List<CommonTopic> topics;


    public MessageCommonFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_message_common, container, false);
        context = view.getContext();
        activity = getActivity();
        initialize();
        return view;
    }

    private void initialize() {
        topics = CommonTopicAdapter.Companion.getInterestOrNormalTopics(context);
        messageCommonRecyclerView = view.findViewById(R.id.message_common_topic_recycler);
        messageCommonRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        messageCommonRecyclerView.setAdapter(new CommonTopicRecyclerAdapter(topics));
        messageCommonRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {
                //여기에서 topics[position]을 사용하는 방식으로 액티비티를 연결하시면 됩니다.
            }
        }));
        progressbar = activity.findViewById(R.id.message_base_progressbar);
        loadingDialog = new LoadingDialog(context);
        setPresenter(new MessagePresenter(context.getApplicationContext(), MessageCommonFragment
                .this, loadingDialog, progressbar));
        refreshImageButton = activity.findViewById(R.id.message_base_refresh_img_btn);
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
