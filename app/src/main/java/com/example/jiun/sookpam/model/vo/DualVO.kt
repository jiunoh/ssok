package com.example.jiun.sookpam.model.vo

import com.example.jiun.sookpam.model.DualModel
import io.realm.RealmModel
import io.realm.annotations.RealmClass
import java.util.*

@RealmClass
open class DualVO : RealmModel{
    lateinit var title:String
    var type:Int = DualModel.RECORD_VO
    var date: String? = null
    var db_id:Int ?= null
}
