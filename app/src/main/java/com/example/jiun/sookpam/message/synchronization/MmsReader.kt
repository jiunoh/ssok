package com.example.jiun.sookpam.message.synchronization

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import com.example.jiun.sookpam.model.vo.MessageVO
import io.realm.Realm
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*
import kotlin.properties.Delegates

class MmsReader(realm: Realm) : MessageReader {
    override var messageList: MessageList = MessageList(realm)
    private val idList = ArrayList<String>()
    private val uri = Uri.parse("content://mms/inbox")
    private var contentResolver: ContentResolver by Delegates.notNull()

    private fun setIdList() {
        val projection: Array<String> = arrayOf("_id")
        val cursor =
                contentResolver.query(uri, projection, null, null, "date DESC")

        if (cursor.moveToFirst()) {
            do {
                val mmsId = cursor.getString(cursor.getColumnIndexOrThrow("_id"))
                idList.add(mmsId)
            } while (cursor.moveToNext())
        }

        cursor.close()
    }

    override fun gatherMessages(context: Context) {
        val projection = arrayOf("_id", "ct_t", "date")
        contentResolver = context.contentResolver
        val cursor =
                contentResolver.query(uri, projection, null, null, "date DESC")

        setIdList()

        if (cursor.moveToFirst()) {
            for (i: Int in messageList.getListBy(MessageVO.TYPE_MMS).size until cursor.count) {
                setDbFieldsFromMessageInbox(cursor)
                cursor.moveToNext()
            }
            cursor.close()
        }
    }

    override fun setDbFieldsFromMessageInbox(cursor: Cursor) {
        val mmsId = cursor.getString(cursor.getColumnIndexOrThrow("_id"))
        // If you do not multiply 1000 to date, then all year of mms printed 1970
        val date = cursor.getLong(cursor.getColumnIndexOrThrow("date")) * 1000
        val mmsDate = Date(java.lang.Long.valueOf(date))
        val phoneNumber: String = getPhoneNumber(mmsId)
        val selection = "mid=$mmsId"
        val partUri = Uri.parse("content://mms/part")
        val partCursor =
                contentResolver.query(partUri, null, selection, null, null)

        if (partCursor.moveToFirst()) {
            do {
                val type = partCursor.getString(partCursor.getColumnIndexOrThrow("ct"))
                if (type == "text/plain") {
                    val body = getMmsBody(partCursor)
                    if (messageList.getBodyNumbersSameWith(body) == 0) {
                        messageList.addToList(phoneNumber, mmsDate, body, MessageVO.TYPE_MMS)
                    }
                }
            } while (partCursor.moveToNext())
        }
        partCursor.close()
    }

    private fun getMmsBody(partCursor: Cursor): String {
        val partId = partCursor.getString(partCursor.getColumnIndexOrThrow("_id"))
        val data = partCursor.getString(partCursor.getColumnIndexOrThrow("_data"))
        return if (data != null) {
            getMessageText(contentResolver, partId)
        } else {
            partCursor.getString(partCursor.getColumnIndexOrThrow("text"))
        }

    }

    @Throws(IOException::class)
    private fun getMessageText(contentResolver: ContentResolver, id: String): String {
        val partUri = Uri.parse("content://mms/part/$id")
        val stringBuilder = StringBuilder()
        val inputStream = contentResolver.openInputStream(partUri)

        if (inputStream != null) {
            val inputStreamReader = InputStreamReader(inputStream, "UTF-8")
            val bufferedReader = BufferedReader(inputStreamReader)
            var temp = bufferedReader.readLine()
            while (temp != null) {
                stringBuilder.append(temp)
                temp = bufferedReader.readLine()
            }
            inputStream.close()
        }

        return stringBuilder.toString()
    }

    @Throws(NumberFormatException::class)
    private fun getPhoneNumber(id: String): String {
        val selection = "msg_id=$id"
        val phoneNumberUri = Uri.parse("content://mms/$id/addr")
        val cursor =
                contentResolver.query(phoneNumberUri, null, selection, null, null)
        var phoneNumber: String? = null

        if (cursor.moveToFirst()) {
            do {
                val address = cursor.getString(cursor.getColumnIndexOrThrow("address"))
                if (address != null) {
                    phoneNumber = address.replace("-", "")
                }
            } while (cursor.moveToNext())
        }

        cursor.close()
        return phoneNumber!!
    }
}