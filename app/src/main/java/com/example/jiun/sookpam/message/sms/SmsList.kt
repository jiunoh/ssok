package com.example.jiun.sookpam.message.sms

import com.example.jiun.sookpam.message.MessageList
import com.example.jiun.sookpam.model.data.SmsVO
import io.realm.Realm
import io.realm.RealmResults
import java.util.*

class SmsList : MessageList<SmsVO> {
    override var realm: Realm = Realm.getDefaultInstance()
    override val messageList: RealmResults<SmsVO> = getList()
    override fun getList(): RealmResults<SmsVO> {
        return realm.where(SmsVO::class.java).findAll()
    }

    override fun addToList(phoneNumber: String, date: Date, body: String) {
        realm.beginTransaction()
        val sms: SmsVO = realm.createObject(SmsVO::class.java, messageList.size.toLong())
        sms.apply {
            this.phoneNumber = phoneNumber
            this.date = date
            this.body = body
        }
        realm.commitTransaction()
    }

    override fun getBodyNumbersSameWith(body: String): Int {
        val selectedBody = realm.where(SmsVO::class.java).contains("body", body).findAll()
        return selectedBody.size
    }
}