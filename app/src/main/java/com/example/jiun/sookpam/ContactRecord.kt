package com.example.jiun.sookpam

import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required

@RealmClass
open class ContactRecord : RealmModel {
    var class1: String? = null
    var class2: String? = null
    var phone: String? = null
}
