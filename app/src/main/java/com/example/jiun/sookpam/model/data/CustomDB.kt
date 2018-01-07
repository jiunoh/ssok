package com.example.jiun.sookpam.model.data

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*
import kotlin.properties.Delegates

open class CustomDB() : RealmObject() {
    @PrimaryKey
    var id: Long = 0
    var category: String = MessageInfo.CATEGORY_DEFAULT
    var messageType: Boolean = MessageInfo.SMS
    var phoneNumber: String by Delegates.notNull()
    var senderName: String? = null
    var date: Date by Delegates.notNull()
    var body: String by Delegates.notNull()
}