package com.example.jiun.ssok.model.vo

import com.example.jiun.ssok.model.DualModel
import io.realm.RealmModel
import io.realm.annotations.RealmClass

@RealmClass
open class DualVO : RealmModel{
    lateinit var title:String
    var type:Int = DualModel.RECORD_VO
    var date: String? = null
    var db_id:Int ?= null
}
