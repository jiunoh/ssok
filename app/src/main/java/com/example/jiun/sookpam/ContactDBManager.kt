package com.example.jiun.sookpam

import android.app.Application
import android.util.Log
import com.example.jiun.sookpam.model.data.ContactVO
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import io.realm.Realm


class ContactDBManager : Application() {
    lateinit private var bufferedReader: BufferedReader
    lateinit var realm: Realm
    lateinit var inputStreamReader : InputStreamReader

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
        val csvFile = "contact.csv"
        inputStreamReader = InputStreamReader(assets.open(csvFile))
        bufferedReader = BufferedReader(inputStreamReader)

        while (true) {
            var line = bufferedReader.readLine() ?: break
            val record = backgroundRealm.createObject(ContactVO::class.java)
            val value = line.split(cvsSplitBy.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            record.class1 = value[0]
            record.class2 = value[1]
            record.phone = "02" + value[2]
        }
    }

    private fun closeBufferedReader() {
        if (bufferedReader != null) {
            try {
                bufferedReader.close()
                inputStreamReader.close()
                assets.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun printLogInSuccess() {
        val results = realm!!.where(ContactVO::class.java).findAll()
        Log.v("SUCCESS", "size : " + results.size)
    }

    fun getCategory(value: String, realm: Realm): String {
        val record = realm.where(ContactVO::class.java).equalTo("phone", value).findFirst()
        if (record == null)
            return "기타"
        else
            return record.class2
    }

    fun getCategoryList(): ArrayList<String> {
        var categoryVOLists = realm.where(ContactVO::class.java).distinctValues("class2").findAll()
        var responseList: ArrayList<String> = ArrayList<String>()

        for (record in categoryVOLists)
            responseList.add(record.class2)
        return responseList
    }
}
