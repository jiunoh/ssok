package com.example.jiun.sookpam.model.sms

import com.example.jiun.sookpam.model.data.SmsVO
import io.realm.Realm
import io.realm.RealmResults
import java.util.*

class SmsList {
    private var realm: Realm = Realm.getDefaultInstance()
    private val smsList: RealmResults<SmsVO> = getSmsList()

    fun getSmsList(): RealmResults<SmsVO> {
        return realm.where(SmsVO::class.java).findAll()
    }

    fun addSmsToList(phoneNumber: String, date: Date, body: String) {
        realm.beginTransaction()
        val sms: SmsVO = realm.createObject(SmsVO::class.java, smsList.size.toLong())
        sms.apply {
            this.phoneNumber = phoneNumber
            this.date = date
            this.body = body
        }
        realm.commitTransaction()
    }

    fun printSmsList() {
        val allSms = realm.where(SmsVO::class.java).findAll()
        allSms.forEach { smsData ->
            println("ID: ${smsData.id} : Date: ${smsData.date} PhoneNumber: ${smsData.phoneNumber} : Body: ${smsData.body}")
        }
    }
}