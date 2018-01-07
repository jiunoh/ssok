package com.example.jiun.sookpam.model.data

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class CustomVO : RealmObject() {
    @PrimaryKey
    var id: Long = 0
    var category: String = MessageInfo.CATEGORY_DEFAULT
    var messageType: Boolean = MessageInfo.SMS
    var phoneNumber: String? = null
    var senderName: String? = null
    var body: String? = null
}