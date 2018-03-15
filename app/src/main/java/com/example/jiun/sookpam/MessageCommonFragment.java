package com.example.jiun.sookpam;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.*;
import android.view.*;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.jiun.sookpam.message.MessageContract;
import com.example.jiun.sookpam.message.MessagePresenter;
import com.example.jiun.sookpam.model.ContactDBManager;
import com.example.jiun.sookpam.model.RecordDBManager;
import com.example.jiun.sookpam.model.vo.RecordVO;
import com.example.jiun.sookpam.util.MsgContentGenerator;
import com.example.jiun.sookpam.util.SharedPreferenceUtil;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class MessageCommonFragment extends Fragment implements MessageContract.View {
    Context context;
    Activity activity;
    View view;
    MessageContract.Presenter presenter;
    ImageButton refreshImageButton;
    LoadingDialog loadingDialog;
    ProgressBar progressbar;
    RecordDBManager recordDBManager;
    ListView messageCommonView;

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
        MessageDepartListAdapter adapter = new MessageDepartListAdapter();
        messageCommonView = view.findViewById(R.id.message_common_listview);
        messageCommonView.setAdapter(adapter);
        context = view.getContext();
        activity = getActivity();
        initialize();
        messageCommonView.setVisibility(View.VISIBLE);
        final ArrayList<RecordVO> dataList = new ArrayList<RecordVO>();
        try {
            recordDBManager = new RecordDBManager(Realm.getDefaultInstance());
            dataList.addAll(recordDBManager.getCommonMessages());
            adapter.addItem(dataList);
        }
        catch (NullPointerException exception) {
            // mesages are not loaded yet
        }
        messageCommonView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RecordVO data = dataList.get(position);
                MsgContentGenerator.showMessageBody(getContext(), data);
            }
        });



        return view;
    }

    private void initialize() {
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
