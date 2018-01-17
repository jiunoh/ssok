package com.example.jiun.sookpam.model.data

import com.example.jiun.sookpam.model.data.MmsVO
import com.example.jiun.sookpam.model.data.SmsVO
import io.realm.RealmModel
import io.realm.annotations.RealmClass

@RealmClass
open class CategoryVO : RealmModel {
    var category : String ?=null
    var sms: SmsVO? = null
    var mms: MmsVO? = null
}
