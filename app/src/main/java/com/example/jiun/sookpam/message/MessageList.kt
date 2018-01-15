package com.example.jiun.sookpam.message

import io.realm.Realm
import io.realm.RealmResults
import java.util.*

interface MessageList<T> {
    var realm: Realm
    val messageList: RealmResults<T>

    fun getList(): RealmResults<T>

    fun addToList(phoneNumber: String, date: Date, body: String)

    fun getBodyNumbersSameWith(body: String): Int
}