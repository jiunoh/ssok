package com.example.jiun.sookpam.model.vo

import io.realm.RealmModel
import io.realm.annotations.RealmClass

@RealmClass
open class ClipVO : RealmModel {
    lateinit var title: String
    var status: Boolean = false
    var category: String ?= null
    var location : String ?= null
}
