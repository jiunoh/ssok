package com.example.jiun.sookpam.message

import android.content.Context
import android.database.Cursor

interface MessageReader<T> {
    var messageList: T

    fun gatherMessages(context: Context)

    fun setDbFieldsFromMessageInbox(cursor: Cursor)
}