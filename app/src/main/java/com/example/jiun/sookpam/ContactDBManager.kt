package com.example.jiun.sookpam

import android.app.Application
import android.util.Log
import com.example.jiun.sookpam.model.data.CategoryVO
import com.example.jiun.sookpam.model.data.ContactVO
import io.realm.Realm
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


class ContactDBManager : Application() {
    lateinit private var bufferedReader: BufferedReader
    lateinit var realm: Realm
    lateinit var inputStreamReader : InputStreamReader
    val VOID:String  = "VOID"

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
                    loadCategory(bgRealm)
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

    private fun loadCategory(backgroundRealm: Realm) {
        val cvsSplitBy = ","
        val csvFile = "category.csv"
        val inputStreamReader = InputStreamReader(assets.open(csvFile))
        val bufferedReader = BufferedReader(inputStreamReader)

        while (true) {
            var line = bufferedReader.readLine() ?: break
            val record = backgroundRealm.createObject(CategoryVO::class.java)
            val value = line.split(cvsSplitBy.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            record.key = value[0]
            record.value = value[1]
            Log.v("Category",record.key+" "+record.value)
        }
        bufferedReader.close()
        inputStreamReader.close()
    }

    fun getCategory(keyword:String?, realm: Realm) : String? {
        if(keyword.equals(VOID))
            return VOID
        else {
            var categoryObj = realm.where(CategoryVO::class.java).equalTo("key", keyword).findFirst()
            return categoryObj?.value ?:"기타"
        }
    }


    fun getCategoryList() :ArrayList<String> {
        var categoryVOList= realm.where(CategoryVO::class.java).distinctValues("value").findAll()
        var responseList: ArrayList<String> = ArrayList<String>()

        for (record in categoryVOList)
            responseList.add(record.value)
        return responseList
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
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun printLogInSuccess() {
        val results = realm!!.where(ContactVO::class.java).findAll()
        Log.v("SUCCESS", "size : " + results.size)
    }

    fun getKeywordOf(value: String, realm: Realm): String {
        val record = realm.where(ContactVO::class.java).equalTo("phone", value).findFirst()
        if (record == null)
            return VOID
        else
            return record.class2
    }

    fun getKeywordList(): ArrayList<String> {
        var keywordVOLists = realm.where(ContactVO::class.java).distinctValues("class2").findAll()
        var responseList: ArrayList<String> = ArrayList<String>()

        for (record in keywordVOLists)
            responseList.add(record.class2)
        return responseList
    }
}
