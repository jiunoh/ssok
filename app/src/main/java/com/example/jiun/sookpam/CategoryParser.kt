package com.example.jiun.sookpam

import android.content.Context
import android.util.Log
import com.example.jiun.sookpam.model.data.MmsVO
import com.example.jiun.sookpam.model.data.SmsVO
import com.example.jiun.sookpam.model.mms.MmsList
import com.example.jiun.sookpam.model.sms.SmsList
import io.realm.Realm

class CategoryParser {
    private var realm: Realm = Realm.getDefaultInstance()

    fun categorizeMessages(context: Context){
        categorizeSMS()
        categorizeMMS()
    }

    fun categorizeSMS() {
        var smsList = SmsList().getSmsList()
        Log.v("SIZE", "smsList size : " + smsList.size)
        for (sms in smsList)
            createSMSCategory(sms)

        Log.v("Category", realm.where(CategoryVO::class.java).findFirst()?.category)
    }

    fun createSMSCategory(sms: SmsVO) {
        realm.executeTransaction { realm ->
            var categoryRecord: CategoryVO = realm.createObject(CategoryVO::class.java)
            val category: String? = getCategory(sms.phoneNumber)
            categoryRecord.category = category
            categoryRecord.sms = sms
        }
    }

    fun categorizeMMS() {
        var mmsList = MmsList().getMmsList()
        for (mms in mmsList)
            createMMSCategory(mms)
    }

    fun createMMSCategory(mms: MmsVO) {
        realm.executeTransaction { realm ->
            var categoryRecord: CategoryVO = realm.createObject(CategoryVO::class.java)
            val category: String? = getCategory(mms.phoneNumber)
            categoryRecord.category = category
            categoryRecord.mms = mms
        }
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