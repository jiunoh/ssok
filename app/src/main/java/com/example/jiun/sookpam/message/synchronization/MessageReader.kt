package com.example.jiun.sookpam.message.synchronization

import android.content.Context
import android.database.Cursor

interface MessageReader {
    var messageList: MessageList

    fun gatherMessages(context: Context)

    fun setDbFieldsFromMessageInbox(cursor: Cursor)
}