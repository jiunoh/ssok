package com.example.jiun.sookpam

import android.content.Context
import com.example.jiun.sookpam.message.MessageList
import com.example.jiun.sookpam.model.data.*
import io.realm.Realm

class CategoryDBManager(val realm: Realm) {
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
        var result = realm.where(CategoryVO::class.java).equalTo("message.body", value).findFirst()
        if (result == null)
            return true
        else
            return false
    }

    fun createMessageCategory(message: MessageVO) {
        realm.executeTransaction { realm ->
            var categoryRecord: CategoryVO = realm.createObject(CategoryVO::class.java)
            val category: String? = context.getCategory(message.phoneNumber, realm)
            categoryRecord.category = category
            categoryRecord.message = message
        }
    }

    fun getDataByCategory(request: String): ArrayList<String> {
        var messageList = realm.where(CategoryVO::class.java).equalTo("category", request).findAll()
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