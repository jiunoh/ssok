package com.example.jiun.sookpam

import android.content.Context
import android.util.Log
import com.example.jiun.sookpam.model.data.SmsVO
import com.example.jiun.sookpam.model.sms.SmsList
import io.realm.Realm

class CategoryParser {
    private var realm: Realm = Realm.getDefaultInstance()

     fun categorizeSMS (context: Context){
        var smsList = SmsList().getSmsList()
         Log.v("SIZE","smsList size : "+smsList.size)
        for (sms in smsList) {
            createCategory(sms)
        }
         Log.v("Category",realm.where(CategoryVO::class.java).findFirst()?.category)
    }

    fun createCategory(sms:SmsVO) {
        realm.executeTransaction { realm ->
            var categoryRecord: CategoryVO = realm.createObject(CategoryVO::class.java)
            val category : String = getCategory(sms.phoneNumber)
            categoryRecord.category = category
            categoryRecord.sms = sms
        }
    }

    fun getCategory(value : String) : String {
        val record = realm!!.where(ContactVO::class.java).equalTo("phone",value).findFirst()
        Log.v("PHONE",value+"/"+record!!.phone)
        if(record == null)
            return "학교"
        else
            return record.class2
    }
}