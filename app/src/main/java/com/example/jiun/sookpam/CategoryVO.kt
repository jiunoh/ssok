package com.example.jiun.sookpam

import com.example.jiun.sookpam.model.data.SmsVO
import io.realm.RealmModel
import io.realm.annotations.RealmClass

@RealmClass
open class CategoryVO : RealmModel {
    lateinit var category : String
    var sms:SmsVO ?=null
}
