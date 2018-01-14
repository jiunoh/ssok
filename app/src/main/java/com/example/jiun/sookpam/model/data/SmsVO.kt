package com.example.jiun.sookpam.model.data

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class SmsVO : RealmObject() {
    @PrimaryKey
    var id: Long = 0
    lateinit var phoneNumber : String
    var date: Date? = null
    var body: String? = null
}