package com.example.jiun.sookpam.model.data

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class DepartmentPhoneNumberVO : RealmObject() {
    @PrimaryKey
    var id: Long = 0
    var class1: String? = null
    var class2: String? = null
    var class3: String? = null
    var class4: String? = null
    var class5: String? = null
    var phoneNumber: String? = null
}