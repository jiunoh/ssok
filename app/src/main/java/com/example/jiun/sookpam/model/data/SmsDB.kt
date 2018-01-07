package com.example.jiun.sookpam.model.data

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*
import kotlin.properties.Delegates

class SmsDB() : RealmObject() {
    @PrimaryKey
    var id: Long = 0
    var phoneNumber: String by Delegates.notNull()
    var date: Date by Delegates.notNull()
    var body: String by Delegates.notNull()
}