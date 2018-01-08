package com.example.jiun.sookpam.model.sms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telephony.SmsMessage
import android.widget.Toast
import java.util.*

class SmsReceiver : BroadcastReceiver() {
    private var smsList: SmsList = SmsList()

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.provider.Telephony.SMS_RECEIVED") {
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
            Toast.makeText(context, "ID: ${smsList.getSmsList().size} : " +
                    "PhoneNumber: ${sms.displayOriginatingAddress} : Body: ${sms.messageBody}", Toast.LENGTH_LONG).show()
            smsList.addSmsToList(sms.displayOriginatingAddress, Date(sms.timestampMillis), sms.messageBody)
        }
    }


}