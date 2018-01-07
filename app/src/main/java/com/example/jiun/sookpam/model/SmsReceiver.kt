package com.example.jiun.sookpam.model

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log
import com.example.jiun.sookpam.model.data.SmsVO
import io.realm.Realm
import io.realm.RealmResults
import java.util.*

class SmsReceiver() : BroadcastReceiver() {
    private lateinit var realm:Realm
    constructor(realm: Realm) : this() {
        this.realm = realm
    }
    private val smsList: RealmResults<SmsVO> = getSmsList()
    private fun getSmsList(): RealmResults<SmsVO> {
        return realm.where(SmsVO::class.java).findAll()
    }

    override fun onReceive(context: Context, intent: Intent) {
        receiveSms(context, intent)
    }

    private fun receiveSms(context: Context, intent: Intent) {
        val extras: Bundle = intent.extras
        val smsExtras = extras.get("pdus") as ArrayList<*>

        for (i: Int in 0..smsExtras.size) {
            val sms: SmsMessage = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                SmsMessage.createFromPdu(smsExtras[i] as ByteArray, extras.getString("format"))
            } else {
                SmsMessage.createFromPdu(smsExtras[i] as ByteArray)
            }
            addSmsToList(sms.displayOriginatingAddress, sms.messageBody)
        }

    }

    private fun addSmsToList(phoneNumber: String, body: String) {
        realm.beginTransaction()
        val sms: SmsVO = realm.createObject(SmsVO::class.java)
        sms.apply {
            id = smsList.size.toLong()
            this.phoneNumber = phoneNumber
            this.body = body
        }
        Log.v("SMS", "ID: " + sms.id + " PhoneNumber: " + sms.phoneNumber + " Body: " + sms.body)
        realm.commitTransaction()
    }
}