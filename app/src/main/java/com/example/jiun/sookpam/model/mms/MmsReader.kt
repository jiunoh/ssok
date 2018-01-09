package com.example.jiun.sookpam.model.mms

import android.content.Context
import android.net.Uri
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import kotlin.properties.Delegates

class MmsReader {
    private var mmsList = MmsList()
    private val uri = Uri.parse("content://mms/part")

    fun getMmsDetails(context: Context) {
        val contentResolver = context.contentResolver
        val cursor = contentResolver.query(uri, null, null, null, null)

        if (cursor.moveToFirst()) {
            for (i: Int in mmsList.getMmsList().size until cursor.count) {
                val partId = cursor.getString(cursor.getColumnIndexOrThrow("_id"))
                val type = cursor.getString(cursor.getColumnIndexOrThrow("ct"))
                var body: String by Delegates.notNull()
                when (type) {
                    "text/plain" -> {
                        val data = cursor.getString(cursor.getColumnIndexOrThrow("_data"))
                        body = if (data != null) {
                            getMmsText(context, partId)
                        } else {
                            cursor.getString(cursor.getColumnIndexOrThrow("text"))
                        }
                        if (mmsList.getSizeOfRow(body)==0) {
                            mmsList.addMmsToList(null, null, body)
                        }
                    }
                }
                cursor.moveToNext()
            }
            mmsList.printMmsList()
        }
        cursor.close()
    }


    private fun getMmsText(context: Context, id: String): String {
        val partUri = Uri.parse("$uri/$id")
        var inputStream: InputStream? = null
        val stringBuilder = StringBuilder()

        try {
            inputStream = context.contentResolver.openInputStream(partUri)
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
}