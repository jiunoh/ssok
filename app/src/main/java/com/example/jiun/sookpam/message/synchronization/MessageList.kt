package com.example.jiun.sookpam.message.synchronization

import com.example.jiun.sookpam.model.vo.MessageVO
import io.realm.Realm
import io.realm.RealmResults
import java.util.*

class MessageList(var realm: Realm) {
    private val messageList: RealmResults<MessageVO> = getList()

    fun getList(): RealmResults<MessageVO> {
        return realm.where(MessageVO::class.java).findAll()
    }

    fun getListBy(messageType: Boolean): RealmResults<MessageVO> {
        return realm.where(MessageVO::class.java)
                .equalTo("messageType", messageType).findAll()
    }

    fun addToList(phoneNumber: String, date: Date, body: String, messageType: Boolean) {
        realm.beginTransaction()
        val mms: MessageVO = realm.createObject(MessageVO::class.java, messageList.size.toLong())
        mms.apply {
            this.phoneNumber = phoneNumber
            this.date = date
            this.body = body
            this.messageType = messageType

        }
        realm.commitTransaction()
    }

    fun getBodyNumbersSameWith(body: String): Int {
        val selectedBody = realm.where(MessageVO::class.java).contains("body", body).findAll()
        return selectedBody.size
    }
}