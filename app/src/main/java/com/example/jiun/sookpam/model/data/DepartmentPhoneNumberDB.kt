package com.example.jiun.sookpam.model.data

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import kotlin.properties.Delegates

class DepartmentPhoneNumberDB() : RealmObject() {
    @PrimaryKey
    var id: Long = 0
    var class1: String by Delegates.notNull()
    var class2: String? = null
    var class3: String? = null
    var class4: String? = null
    var class5: String? = null
    var phoneNumber: ArrayList<String> by Delegates.notNull()
}