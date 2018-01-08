package com.example.jiun.sookpam.model

import android.content.Context
import android.database.Cursor
import android.net.Uri
import com.example.jiun.sookpam.model.data.SmsVO
import io.realm.Realm
import io.realm.RealmResults
import java.util.*

class SmsReader {
    private var realm: Realm = Realm.getDefaultInstance()
    private val smsList: RealmResults<SmsVO> = getSmsList()

    private fun getSmsList(): RealmResults<SmsVO> {
        return realm.where(SmsVO::class.java).findAll()
    }

    fun getSmsDetails(context: Context) {
        val uri: Uri = Uri.parse("content://sms/inbox")
        val cursor: Cursor = context.contentResolver.query(uri, null, null, null, null)

        if (cursor.moveToFirst()) {
            for (i: Int in smsList.size until cursor.count) {
                val phoneNumber: String = cursor.getString(cursor.getColumnIndexOrThrow("address"))
                val date: String = cursor.getString(cursor.getColumnIndexOrThrow("date"))
                val smsDayTime = Date(java.lang.Long.valueOf(date))
                val body: String = cursor.getString(cursor.getColumnIndexOrThrow("body"))
                val type: String = cursor.getString(cursor.getColumnIndexOrThrow("type"))

                addSmsToList(phoneNumber, body)
                cursor.moveToNext()
            }
        }
        cursor.close()

        val allSms = realm.where(SmsVO::class.java).findAll()
        allSms.forEach { smsData ->
            println("ID: ${smsData.id} : PhoneNumber: ${smsData.phoneNumber} : Body: ${smsData.body}")
        }
    }

    private fun addSmsToList(phoneNumber: String, body: String) {
        realm.beginTransaction()
        val sms: SmsVO = realm.createObject(SmsVO::class.java, smsList.size.toLong())
        sms.apply {
            this.phoneNumber = phoneNumber
            this.body = body
        }
        realm.commitTransaction()
    }
}