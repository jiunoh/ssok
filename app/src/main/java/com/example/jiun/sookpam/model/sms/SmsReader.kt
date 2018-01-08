package com.example.jiun.sookpam.model.sms

import android.content.Context
import android.database.Cursor
import android.net.Uri
import java.util.*

class SmsReader {
    private var smsList: SmsList = SmsList()

    fun getSmsDetails(context: Context) {
        val uri: Uri = Uri.parse("content://sms/inbox")
        val cursor: Cursor = context.contentResolver.query(uri, null, null, null, null)

        if (cursor.moveToFirst()) {
            for (i: Int in smsList.getSmsList().size until cursor.count) {
                val phoneNumber: String = cursor.getString(cursor.getColumnIndexOrThrow("address"))
                val date: String = cursor.getString(cursor.getColumnIndexOrThrow("date"))
                val smsDayTime = Date(java.lang.Long.valueOf(date))
                val body: String = cursor.getString(cursor.getColumnIndexOrThrow("body"))

                smsList.addSmsToList(phoneNumber, smsDayTime, body)
                cursor.moveToNext()
            }
            smsList.printSmsList()
        }
        cursor.close()
    }
}