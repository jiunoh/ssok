package com.example.jiun.sookpam.model.data

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class SmsVO : RealmObject() {
    @PrimaryKey
    var id: Long = 0
    var phoneNumber: String?=null
    var body: String?=null
}