package com.example.jiun.sookpam.model

import android.content.Context
import com.example.jiun.sookpam.message.MessageList
import com.example.jiun.sookpam.model.vo.*
import io.realm.Realm
import java.util.*

class RecordDBManager(val realm: Realm) {
    private lateinit var context: ContactDBManager

    fun categorizeMessages(context: Context) {
        this.context = context as ContactDBManager
        var messageList = MessageList(realm).getList()
        for (message in messageList) {
            if (fromUniversity(message.phoneNumber) and doesNotExist(message.body))
                createMessageCategory(message)
        }
    }

    private fun fromUniversity(phoneNumber: String?): Boolean {
        var result = realm.where(ContactVO::class.java).equalTo("phone", phoneNumber).findFirst()
        return (result != null)
    }

    private fun doesNotExist(value: String?): Boolean {
        var result = realm.where(RecordVO::class.java).equalTo("message.body", value).findFirst()
        return (result == null)
    }

    private fun createMessageCategory(message: MessageVO) {
        realm.executeTransaction { realm ->
            var recordRecord: RecordVO = realm.createObject(RecordVO::class.java)
            val department: String? = context.getDepartmentOf(message.phoneNumber, realm)
            recordRecord.message = message
            recordRecord.division = department
            recordRecord.category = context.getCategory(department,realm)
        }
    }

    fun contains(query : String): ArrayList<RecordVO>{
        var msgResponse = realm.where(RecordVO::class.java).contains("message.body", query).findAll()
        var responseList: ArrayList<RecordVO> = ArrayList<RecordVO>()
        responseList.addAll(msgResponse)
        return responseList
    }
}