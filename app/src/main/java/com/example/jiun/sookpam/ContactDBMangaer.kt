package com.example.jiun.sookpam

import android.app.Application
import android.util.Log

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.ArrayList
import java.util.Arrays

import io.realm.Realm
import io.realm.RealmConfiguration


class ContactDBMangaer : Application() {
    lateinit private var bufferedReader: BufferedReader
    lateinit var realm: Realm

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        realm = Realm.getDefaultInstance()
        //var config : RealmConfiguration = RealmConfiguration.Builder().migration(ContactMigration()).build()
        //realm = Realm.getInstance(config)

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
            val record = backgroundRealm.createObject(ContactVO::class.java)
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
        val results = realm!!.where(ContactVO::class.java).findAll()
        Log.v("SUCCESS", "size : " + results.size)
    }

    fun getCategory(value: String?): String {
        val record = realm!!.where(ContactVO::class.java).equalTo("phone", value).findFirst()
        Log.v("PHONE", value + "/" + record!!.phone)
        if (record == null)
            return "학교"
        else
            return record.class2
    }
}
