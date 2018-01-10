package com.example.jiun.sookpam;

import android.app.Application;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;


public class ContactDBMangaer extends Application {
    private static String TAG = "TOTAL";
    private Realm realm;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        realm = Realm.getDefaultInstance();

        //adding to db satrt
        if (!(realm.isEmpty())) {
            Log.v("DB", "already there!!");
        } else {
            Log.v("DB", "Not Found!!");
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm bgRealm) {
                    String csvFile = "total.csv";
                    BufferedReader br = null;
                    String line = "";
                    String cvsSplitBy = ",";
                    try {
                        br = new BufferedReader(new InputStreamReader(getAssets().open(csvFile)));

                        //ContactRecord user = bgRealm.createObject(ContactRecord.class);
                        while ((line = br.readLine()) != null) {
                            // use comma as separator
                            ContactRecord record = bgRealm.createObject(ContactRecord.class);
                            final String[] value = line.split(cvsSplitBy);
                            record.setClass1(value[0]);
                            record.setClass2(value[1]);
                            record.setPhone(value[2]);
                            //Log.i(TAG,user.getClass1()+"/"+user.getClass2()+"/"+user.getPhone()); //test 코드
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.i(TAG, "file Not found wrong directory");
                    } finally {
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
                    printLogInSuccess();
                }
            }, new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    error.printStackTrace();
                    Log.v("TAGGED", "FAILED");
                }
            });

        }

    }

    private void printLogInSuccess() {
        RealmResults<ContactRecord> results = realm.where(ContactRecord.class).findAll();
        Log.v("SUCCESS", "size : " + results.size());
        for (ContactRecord record : results) {
            Log.v(TAG, record.getClass1() + "/" + record.getClass2() + "/" + record.getPhone()); //test 코드
        }
    }



}
