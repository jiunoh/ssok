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

    lateinit private var bufferedReader: BufferedReader

    companion object {
        private val TAG = "ContactDBManager"
        private var realm: Realm? = null
    }

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        realm = Realm.getDefaultInstance()

        if (!realm!!.isEmpty) {
            Log.v("DB", "already there!!")
        } else {
            Log.v("DB", "Not Found!!")
            realm!!.executeTransactionAsync({ bgRealm ->
                try {
                    parseStringToRealm(bgRealm)
                } catch (exception: IOException) {
                    exception.printStackTrace()
                } finally {
                    closeBufferedReader()
                }
            }, { printLogInSuccess() }) { error ->
                error.printStackTrace()
                Log.v("TAGGED", "FAILED")
            }
        }
    }

    private fun parseStringToRealm(backgroundRealm: Realm) {
        val cvsSplitBy = ","
        val csvFile = "total.csv"
        bufferedReader = BufferedReader(InputStreamReader(assets.open(csvFile)))

        while (true) {
            var line = bufferedReader.readLine() ?: break;
            val record = backgroundRealm.createObject(ContactRecord::class.java)
            val value = line.split(cvsSplitBy.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            record.class1 = value[0]
            record.class2 = value[1]
            record.phone = value[2]
        }
    }

    private fun closeBufferedReader() {
        if (bufferedReader != null) {
            try {
                bufferedReader.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    private fun printLogInSuccess() {
        val results = realm!!.where(ContactRecord::class.java).findAll()
        Log.v("SUCCESS", "size : " + results.size)
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


}
