package com.example.jiun.ssok.model

import android.app.Application
import android.util.Log
import com.example.jiun.ssok.model.vo.CategoryVO
import com.example.jiun.ssok.model.vo.ContactVO
import io.realm.Realm
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class ContactDBManager : Application() {
    lateinit private var bufferedReader: BufferedReader
    lateinit var realm: Realm
    lateinit var inputStreamReader: InputStreamReader

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        try {
            realm = Realm.getDefaultInstance()
            realm!!.executeTransactionAsync({ bgRealm ->
                try {
                    if (doesNotExist(bgRealm)) {
                        loadContact(bgRealm)
                        loadCategory(bgRealm)
                    }
                } catch (exception: IOException) {
                    exception.printStackTrace()
                } finally {
                    if (doesNotExist(bgRealm))
                        closeBufferedReader()
                }
            }, { printLogInSuccess() }) { error ->
                error.printStackTrace()
                Log.v("TAGGED", "FAILED")
            }
        } catch (exception: RuntimeException) {
            //Realm already exist
        } catch (exception: UninitializedPropertyAccessException) {
            //Realm already exist
        }
    }

    private fun doesNotExist(backgroundRealm: Realm): Boolean {
        val existence = backgroundRealm.where(ContactVO::class.java).findFirst()
        return existence == null
    }

    private fun loadContact(backgroundRealm: Realm) {
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
        }
        bufferedReader.close()
        inputStreamReader.close()
    }

    fun getCategory(division: String?, realm: Realm): String? {
        var categoryObj = realm.where(CategoryVO::class.java).equalTo("key", division).findFirst()
        return categoryObj?.value ?: "공통"
    }

    fun getDivisionList(category: String?, realm:Realm): ArrayList<String> {
        var divisionObjList = realm.where(CategoryVO::class.java).equalTo("value", category).findAll()
        var responseList: ArrayList<String> = ArrayList<String>()
        for (record in divisionObjList)
            responseList.add(record.key)

        return responseList
    }

    fun getCategoryList(): ArrayList<String> {
        var categoryVOList = realm.where(CategoryVO::class.java).distinctValues("value").findAll()
        var responseList: ArrayList<String> = ArrayList<String>()

        for (record in categoryVOList)
            responseList.add(record.value)
        return responseList
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

    fun getDepartmentOf(value: String, realm: Realm): String {
        val record = realm.where(ContactVO::class.java).equalTo("phone", value).findFirst()
        return record!!.class2
    }

    fun getDepartmentList(): ArrayList<String> {
        var departmentLists = realm.where(ContactVO::class.java).distinctValues("class2").findAll()
        var responseList: ArrayList<String> = ArrayList<String>()

        for (record in departmentLists)
            responseList.add(record.class2)
        return responseList
    }

    fun getInfo(division: String): String {
        val result = realm.where(ContactVO::class.java).distinctValues("class2").findFirst()
        return result!!.phone
    }
}
