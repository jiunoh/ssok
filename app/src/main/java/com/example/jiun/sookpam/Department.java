package com.example.jiun.sookpam;

import android.app.Application;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import io.realm.Realm;


public class Department extends Application {
    private  static String TAG = "FILENOTFOUND";
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        // Get a Realm instance for this thread
        // The RealmConfiguration is created using the builder pattern.

        // Use the config
        Realm realm = Realm.getDefaultInstance();


        //adding to db satrt
        if (!(realm.isEmpty())) {
            Log.v("DB", "already there!!");
        } else {
            Log.v("DB", "Not Found!!");
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm bgRealm) {
                    String csvFile = "department.csv";
                    BufferedReader br = null;
                    String line = "";
                    String cvsSplitBy = ",";
                    try {
                        br = new BufferedReader(new InputStreamReader(getAssets().open(csvFile)));

                        ContactRecord user = bgRealm.createObject(ContactRecord.class);
                        while ((line = br.readLine()) != null) {
                            // use comma as separator
                            final String[] record = line.split(cvsSplitBy);
                            user.setClass1(record[0]);
                            user.setClass2(record[1]);
                            user.setPhone(record[2]);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.i(TAG, "file Not found wrong directory");
                    }
                    finally {
                        if (br != null) {
                            try {
                                br.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    Log.v("TAGGED", "SAVED");
                }
            }, new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    Log.v("TAGGED", "FAILED");
                }
            });
        }

    }
}
