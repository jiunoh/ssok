package com.example.jiun.sookpam.searchable;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class ConnReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            NetworkInfo info = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
            NetworkInfo.DetailedState state = info.getDetailedState();
            if (state == NetworkInfo.DetailedState.CONNECTED) {
                Toast.makeText(context, "연결되었습니다.", Toast.LENGTH_LONG).show();
            } else if (state == NetworkInfo.DetailedState.DISCONNECTED) {
                Toast.makeText(context, "연결이 끊어졌습니다.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
