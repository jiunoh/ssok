package com.example.jiun.sookpam

import android.app.Application
import android.util.Log

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.ArrayList
import java.util.Arrays

import io.realm.Realm
import io.realm.RealmResults


class ContactDBMangaer : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        realm = Realm.getDefaultInstance()

        //adding to db satrt
        if (!realm!!.isEmpty) {
            Log.v("DB", "already there!!")
        } else {
            Log.v("DB", "Not Found!!")
            realm!!.executeTransactionAsync({ bgRealm ->
                val csvFile = "total.csv"
                var br: BufferedReader? = null
                var line = ""
                val cvsSplitBy = ","
                try {
                    br = BufferedReader(InputStreamReader(assets.open(csvFile)))

                    //ContactRecord user = bgRealm.createObject(ContactRecord.class);
                    while (true) {
                        line = br.readLine() ?: break;
                        // use comma as separator
                        val record = bgRealm.createObject(ContactRecord::class.java)
                        val value = line.split(cvsSplitBy.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        record.class1 = value[0]
                        record.class2 = value[1]
                        record.phone = value[2]
                        //Log.i(TAG,user.getClass1()+"/"+user.getClass2()+"/"+user.getPhone()); //test 코드
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    Log.i(TAG, "file Not found wrong directory")
                } finally {
                    if (br != null) {
                        try {
                            br.close()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }

                    }
                }
            }, { printLogInSuccess() }) { error ->
                error.printStackTrace()
                Log.v("TAGGED", "FAILED")
            }
        }
    }

    private fun printLogInSuccess() {
        val results = realm!!.where(ContactRecord::class.java).findAll()
        Log.v("SUCCESS", "size : " + results.size)
        for (record in results) {
            Log.v(TAG, record.class1 + "/" + record.class2 + "/" + record.phone) //test 코드
        }
    }

    fun getRequest(column: String, value: String) {
        val results = realm!!.where(ContactRecord::class.java).distinctValues("class2").findAll()
        Log.v("SUCCESS", "size : " + results.size)
        for (record in results) {
            Log.v(TAG, record.class1 + "/" + record.class2 + "/" + record.phone) //test 코드
        }
        val temp = results.toTypedArray() as Array<ContactRecord>
        val list = ArrayList(Arrays.asList(*temp))
    }

    companion object {
        private val TAG = "ContactDBManager"
        private var realm: Realm? = null
    }


}
