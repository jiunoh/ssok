package com.example.jiun.sookpam.model

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telephony.SmsMessage
import android.widget.Toast
import com.example.jiun.sookpam.model.data.SmsVO
import io.realm.Realm
import io.realm.RealmResults

class SmsReceiver : BroadcastReceiver() {
    private var realm: Realm = Realm.getDefaultInstance()
    private val smsList: RealmResults<SmsVO> = getSmsList()
    private fun getSmsList(): RealmResults<SmsVO> {
        return realm.where(SmsVO::class.java).findAll()
    }

    override fun onReceive(context: Context, intent: Intent) {
        if(intent.action == "android.provider.Telephony.SMS_RECEIVED") {
            receiveSms(context, intent)
        }
    }

    private fun receiveSms(context: Context, intent: Intent) {
        val extras: Bundle = intent.extras
        val smsExtras = extras.get("pdus") as Array<*>

        for (i: Int in 0 until smsExtras.size) {
            val sms: SmsMessage = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                SmsMessage.createFromPdu(smsExtras[i] as ByteArray, extras.getString("format"))
            } else {
                SmsMessage.createFromPdu(smsExtras[i] as ByteArray)
            }
            Toast.makeText(context, "ID: " + smsList.size + " PhoneNumber: " + sms.displayOriginatingAddress + " Body: " + sms.messageBody, Toast.LENGTH_LONG).show()
            addSmsToList(sms.displayOriginatingAddress, sms.messageBody)
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
        val allSms = realm.where(SmsVO::class.java).findAll()
        allSms.forEach{smsData->
            println("ID: ${smsData.id} : PhoneNumber: ${smsData.phoneNumber} : Body: ${smsData.body}")
        }
    }
}