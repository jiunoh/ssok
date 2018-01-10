package com.example.jiun.sookpam.model.sms

import android.content.Context
import android.database.Cursor
import android.net.Uri
import java.util.*

class SmsReader {
    private var smsList: SmsList = SmsList()

    fun setSms(context: Context) {
        val uri: Uri = Uri.parse("content://sms/inbox")
        val contentResolver = context.contentResolver
        val cursor: Cursor = contentResolver.query(uri, null, null, null, null)

        if (cursor.moveToFirst()) {
            for (i: Int in smsList.getSmsList().size until cursor.count) {
                setSmsField(cursor)
                cursor.moveToNext()
            }
            smsList.printSmsList()
        }
        cursor.close()
    }

    private fun setSmsField(cursor: Cursor) {
        val phoneNumber: String = cursor.getString(cursor.getColumnIndexOrThrow("address"))
        val date: String = cursor.getString(cursor.getColumnIndexOrThrow("date"))
        val smsDayTime = Date(java.lang.Long.valueOf(date))
        val body: String = cursor.getString(cursor.getColumnIndexOrThrow("body"))

        smsList.addSmsToList(phoneNumber, smsDayTime, body)
    }
}