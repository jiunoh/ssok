package com.example.jiun.sookpam.model.mms

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*
import kotlin.properties.Delegates

class MmsReader {
    private var mmsList = MmsList()
    private val idList = ArrayList<String>()
    private val uri = Uri.parse("content://mms/inbox")

    fun getMmsDetails(context: Context) {
        val projection = arrayOf("_id", "ct_t", "date")
        val contentResolver = context.contentResolver
        val cursor = contentResolver.query(uri, projection, null, null, "date DESC")

        setIdList(contentResolver)

        if (cursor.moveToFirst()) {
            do {
                val mmsId = cursor.getString(cursor.getColumnIndexOrThrow("_id"))
                val date = cursor.getString(cursor.getColumnIndexOrThrow("date"))
                val mmsDayTime = Date(java.lang.Long.valueOf(date))
                val mmsType = cursor.getString(cursor.getColumnIndexOrThrow("ct_t"))

                if (mmsType.startsWith("application/vnd.wap.multipart")) {
                    val phoneNumber = getPhoneNumber(contentResolver, mmsId)
                    val selection = "mid=$mmsId"
                    val partUri = Uri.parse("content://mms/part")
                    val partCursor = contentResolver.query(partUri, null, selection, null, null)

                    if (partCursor.moveToFirst()) {
                        do {
                            val partId = partCursor.getString(partCursor.getColumnIndexOrThrow("_id"))
                            val type = partCursor.getString(partCursor.getColumnIndexOrThrow("ct"))
                            var body: String by Delegates.notNull()
                            if (type == "text/plain") {
                                val data = partCursor.getString(partCursor.getColumnIndexOrThrow("_data"))
                                body = if (data != null) {
                                    getMmsText(contentResolver, partId)
                                } else {
                                    partCursor.getString(partCursor.getColumnIndexOrThrow("text"))
                                }
                                if (mmsList.getSizeOfRow(body) == 0) {
                                    mmsList.addMmsToList(phoneNumber, mmsDayTime, body)
                                }

                            }
                        } while (partCursor.moveToNext())
                    }
                    partCursor.close()
                }
            } while (cursor.moveToNext())
            cursor.close()
            mmsList.printMmsList()
        }
    }

    private fun setIdList(contentResolver: ContentResolver) {
        val projection: Array<String> = arrayOf("_id")
        val cursor = contentResolver.query(uri, projection, null, null, "date DESC")

        if (cursor.moveToFirst()) {
            do {
                val mmsId = cursor.getString(cursor.getColumnIndexOrThrow("_id"))
                idList.add(mmsId)
            } while (cursor.moveToNext())
        }

        cursor.close()
    }

    private fun getMmsText(contentResolver: ContentResolver, id: String): String {
        val partUri = Uri.parse("content://mms/part/$id")
        var inputStream: InputStream? = null
        val stringBuilder = StringBuilder()

        try {
            inputStream = contentResolver.openInputStream(partUri)
            if (inputStream != null) {
                val inputStreamReader = InputStreamReader(inputStream, "UTF-8")
                val bufferedReader = BufferedReader(inputStreamReader)
                var temp = bufferedReader.readLine()
                while (temp != null) {
                    stringBuilder.append(temp)
                    temp = bufferedReader.readLine()
                }
            }
        } catch (e: IOException) {
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close()
                } catch (e: IOException) {
                }
            }
        }
        return stringBuilder.toString()
    }

    private fun getPhoneNumber(contentResolver: ContentResolver, id: String): String {
        val selection = "msg_id=$id"
        val phoneNumberUri = Uri.parse("content://mms/$id/addr")
        val cursor = contentResolver.query(phoneNumberUri, null, selection, null, null)
        var phoneNumber: String? = null

        if (cursor.moveToFirst()) {
            do {
                val address = cursor.getString(cursor.getColumnIndexOrThrow("address"))
                if (address != null) {
                    try {
                        phoneNumber = address.replace("-", "")
                    } catch (numberFormatException: NumberFormatException) {
                        if (phoneNumber == null) {
                            phoneNumber = address
                        }
                    }
                }
            } while (cursor.moveToNext())
        }

        cursor.close()
        return phoneNumber!!
    }
}