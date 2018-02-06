package com.example.jiun.sookpam.model.data

import io.realm.RealmModel
import io.realm.annotations.RealmClass

@RealmClass
open class RecordVO : RealmModel {
    var category : String ?=null
    var message: MessageVO? = null
    var keyword : String ?= null
}
