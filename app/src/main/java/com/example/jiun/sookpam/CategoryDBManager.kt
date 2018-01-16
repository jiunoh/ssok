package com.example.jiun.sookpam

import android.content.Context
import android.provider.Telephony
import android.util.Log
import com.example.jiun.sookpam.model.data.CategoryVO
import com.example.jiun.sookpam.model.data.ContactVO
import com.example.jiun.sookpam.model.data.MmsVO
import com.example.jiun.sookpam.model.data.SmsVO
import com.example.jiun.sookpam.model.mms.MmsList
import com.example.jiun.sookpam.model.sms.SmsList
import io.realm.Realm
import io.realm.RealmResults

class CategoryDBManager {
    private var realm: Realm = Realm.getDefaultInstance()
    private lateinit var context: ContactDBManager
    fun categorizeMessages(context: Context) {
        this.context = context as ContactDBManager
        categorizeSMS()
        categorizeMMS()
        //testParser()
    }

    fun categorizeSMS() {
        var smsList = SmsList().getSmsList()
        for (sms in smsList) {
            if (doesSMSNotExist(sms.body))
                createSMSCategory(sms)
        }
    }

    fun doesSMSNotExist(value: String?): Boolean {
        var result = realm.where(CategoryVO::class.java).equalTo("sms.body", value).findFirst()
        if (result == null)
            return true
        else
            return false
    }


    fun createSMSCategory(sms: SmsVO) {
        realm.executeTransaction { realm ->
            var categoryRecord: CategoryVO = realm.createObject(CategoryVO::class.java)
            val category: String? = context.getCategory(sms.phoneNumber)
            categoryRecord.category = category
            categoryRecord.sms = sms
        }
    }

    fun categorizeMMS() {
        var mmsList = MmsList().getMmsList()
        for (mms in mmsList) {
            if (doesMMSNotExist(mms.body))
                createMMSCategory(mms)
        }
    }

    fun doesMMSNotExist(value: String?): Boolean {
        var result = realm.where(CategoryVO::class.java).equalTo("mms.body", value).findFirst()
        if (result == null)
            return true
        else
            return false
    }

    fun createMMSCategory(mms: MmsVO) {
        realm.executeTransaction { realm ->
            var categoryRecord: CategoryVO = realm.createObject(CategoryVO::class.java)
            val category: String? = context.getCategory(mms.phoneNumber)
            categoryRecord.category = category
            categoryRecord.mms = mms
        }
    }

    fun testParser() {
        var messageList = realm.where(CategoryVO::class.java).findAll()
        Log.v("SIZE", "smsList size : " + messageList.size)
        for (sms in messageList)
            Log.v("Categories", sms.category)
    }

    fun getDateByCategory(request: String): ArrayList<String> {
        var messageList = realm.where(CategoryVO::class.java).equalTo("category", request).findAll()
        var responseList: ArrayList<String> = ArrayList<String>()

        for (record in messageList) {
            if (record.mms != null) {
                val msgBody: String by lazy<String> { (record.mms as MmsVO).body as String }
                responseList.add(msgBody)
            } else {
                val msgBody: String by lazy<String> { (record.sms as SmsVO).body as String }
                responseList.add(msgBody)
            }
        }
        return responseList
    }

    fun refreshCategories() {
        realm.executeTransaction { realm ->
            var messageList = realm.where(CategoryVO::class.java).findAll()
            for (record in messageList) {
                if (record.mms != null)
                    record.category = context.getCategory((record.mms)?.phoneNumber)
                else
                    record.category = context.getCategory((record.sms)?.phoneNumber)
            }
        }
    }
}