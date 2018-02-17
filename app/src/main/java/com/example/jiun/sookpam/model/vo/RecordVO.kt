package com.example.jiun.sookpam.model.vo

import io.realm.RealmModel
import io.realm.annotations.RealmClass

@RealmClass
open class RecordVO : RealmModel {
    var category: String? = null
    var division: String? = null
    var message: MessageVO? = null
}
