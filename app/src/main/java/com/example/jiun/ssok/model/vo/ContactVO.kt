package com.example.jiun.ssok.model.vo

import io.realm.RealmModel
import io.realm.annotations.RealmClass

@RealmClass
open class ContactVO : RealmModel {
    lateinit var class1: String
    lateinit var class2: String
    lateinit var phone: String
}
