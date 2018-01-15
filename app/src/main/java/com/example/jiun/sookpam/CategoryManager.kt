package com.example.jiun.sookpam

import android.content.Context
import android.util.Log
import com.example.jiun.sookpam.model.data.CategoryVO
import com.example.jiun.sookpam.model.data.ContactVO
import com.example.jiun.sookpam.model.data.MmsVO
import com.example.jiun.sookpam.model.data.SmsVO
import com.example.jiun.sookpam.model.mms.MmsList
import com.example.jiun.sookpam.model.sms.SmsList
import io.realm.Realm
import io.realm.RealmResults

class CategoryManager {
    private var realm: Realm = Realm.getDefaultInstance()
    private lateinit var context: ContactDBManager
    fun categorizeMessages(context: Context) {
        this.context = context as ContactDBManager
        categorizeSMS()
        categorizeMMS()
        testParser()
    }

    fun categorizeSMS() {
        var smsList = SmsList().getSmsList()
        for (sms in smsList) {
            if (doesSMSNotExist(sms.body))
                createSMSCategory(sms)
        }
    }

    fun doesSMSNotExist(value: String?): Boolean {
        var result = realm.where(CategoryVO::class.java).contains("sms.body", value).findAll()
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
        var result = realm.where(CategoryVO::class.java).contains("mms.body", value).findAll()
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

    fun getCategoryList() : RealmResults<ContactVO>  {
        var categoryVOLists = realm.where(ContactVO::class.java).distinctValues("class2").findAll()
        return categoryVOLists
    }

}