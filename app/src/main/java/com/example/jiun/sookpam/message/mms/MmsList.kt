package com.example.jiun.sookpam.message.mms

import com.example.jiun.sookpam.message.MessageList
import com.example.jiun.sookpam.model.data.MmsVO
import io.realm.Realm
import io.realm.RealmResults
import java.util.*

class MmsList(override var realm: Realm) : MessageList<MmsVO> {
    override val messageList: RealmResults<MmsVO> = getList()

    override fun getList(): RealmResults<MmsVO> {
        return realm.where(MmsVO::class.java).findAll()
    }

    override fun addToList(phoneNumber: String, date: Date, body: String) {
        realm.beginTransaction()
        val mms: MmsVO = realm.createObject(MmsVO::class.java, messageList.size.toLong())
        mms.apply {
            this.phoneNumber = phoneNumber
            this.date = date
            this.body = body
        }
        realm.commitTransaction()
    }

    override fun getBodyNumbersSameWith(body: String): Int {
        val selectedBody = realm.where(MmsVO::class.java).contains("body", body).findAll()
        return selectedBody.size
    }
}