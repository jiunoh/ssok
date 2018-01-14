package com.example.jiun.sookpam

import android.util.Log
import com.example.jiun.sookpam.model.data.SmsVO
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where

class CategoryParser {
    private var realm: Realm = Realm.getDefaultInstance()

    public fun categorizeSMS (){
        var smsList:RealmResults<SmsVO> = realm.where<SmsVO>().findAll()
        smsList.forEach { sms-> Log.v("SMS",sms.phoneNumber) }
    }
}