package com.example.jiun.sookpam

import android.content.Context
import com.example.jiun.sookpam.message.MessageList
import com.example.jiun.sookpam.model.data.*
import io.realm.Realm

class RecordDBManager(val realm: Realm) {
    private lateinit var context: ContactDBManager
    fun categorizeMessages(context: Context) {
        this.context = context as ContactDBManager
        var messageList = MessageList(realm).getList()
        for (message in messageList) {
            if (doesMessageNotExist(message.body))
                createMessageCategory(message)
        }
    }

    fun doesMessageNotExist(value: String?): Boolean {
        var result = realm.where(RecordVO::class.java).equalTo("message.body", value).findFirst()
        return (result == null)
    }

    fun createMessageCategory(message: MessageVO) {
        realm.executeTransaction { realm ->
            var recordRecord: RecordVO = realm.createObject(RecordVO::class.java)
            val keyword: String? = context.getKeywordOf(message.phoneNumber, realm)
            recordRecord.keyword = keyword
            recordRecord.message = message
            recordRecord.category = context.getCategory(keyword,realm)
        }
    }

    fun getDataByCategory(request: String): ArrayList<String> {
        var messageList = realm.where(RecordVO::class.java).equalTo("category", request).findAll()
        var responseList: ArrayList<String> = ArrayList<String>()

        for (record in messageList) {
            if (record.message != null) {
                val msgBody: String by lazy<String> { (record.message as MessageVO).body }

                responseList.add(msgBody)
            }
        }
        return responseList
    }
}