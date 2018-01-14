package com.example.jiun.sookpam

import io.realm.RealmModel
import io.realm.annotations.RealmClass

@RealmClass
open class ContactVO : RealmModel {
    lateinit var class1: String
    lateinit var class2: String
    lateinit var phone: String
}
