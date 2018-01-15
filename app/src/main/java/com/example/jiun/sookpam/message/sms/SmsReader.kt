package com.example.jiun.sookpam.message.sms

import android.content.Context
import android.database.Cursor
import android.net.Uri
import com.example.jiun.sookpam.message.MessageReader
import java.util.*

class SmsReader : MessageReader<SmsList> {
    override var messageList: SmsList=SmsList()

    override fun gatherMessages(context: Context) {
        val uri: Uri = Uri.parse("content://sms/inbox")
        val contentResolver = context.contentResolver
        val cursor: Cursor = contentResolver.query(uri, null, null, null, null)

        if (cursor.moveToFirst()) {
            for (i: Int in messageList.getList().size until cursor.count) {
                setDbFieldsFromMessageInbox(cursor)
                cursor.moveToNext()
            }
        }
        cursor.close()
    }

    override fun setDbFieldsFromMessageInbox(cursor: Cursor) {
        val phoneNumber: String = cursor.getString(cursor.getColumnIndexOrThrow("address"))
        val date: String = cursor.getString(cursor.getColumnIndexOrThrow("date"))
        val smsDayTime = Date(java.lang.Long.valueOf(date))
        val body: String = cursor.getString(cursor.getColumnIndexOrThrow("body"))

        if (messageList.getBodyNumbersSameWith(body) == 0) {
            messageList.addToList(phoneNumber, smsDayTime, body)
        }
    }
}