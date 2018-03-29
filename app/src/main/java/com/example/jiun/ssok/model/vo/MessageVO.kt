package com.example.jiun.ssok.model.vo

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class MessageVO : RealmObject() {
    @PrimaryKey
    var id: Long = 0
    lateinit var phoneNumber: String
    lateinit var date: Date
    lateinit var body: String
    var messageType: Boolean = true

    companion object {
        const val TYPE_SMS = true
        const val TYPE_MMS = false
    }
}